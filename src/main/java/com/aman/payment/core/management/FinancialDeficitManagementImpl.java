package com.aman.payment.core.management;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.aman.payment.core.mapper.FinancialDeficitMapper;
import com.aman.payment.core.model.FinancialDeficit;
import com.aman.payment.core.model.PullAccount;
import com.aman.payment.core.model.dto.FinancialDeficitDTO;
import com.aman.payment.core.model.dto.FinancialDeficitPagingDTO;
import com.aman.payment.core.model.payload.ApprovedFinancialDeficitRequest;
import com.aman.payment.core.model.payload.CloseFinancialDeficitRequest;
import com.aman.payment.core.model.payload.FinancialDeficitByUserRequest;
import com.aman.payment.core.model.payload.URLRequest;
import com.aman.payment.core.service.CryptoMngrCoreService;
import com.aman.payment.core.service.FinancialDeficitService;
import com.aman.payment.core.service.PullAccountService;
import com.aman.payment.util.StatusConstant;

@Component
public class FinancialDeficitManagementImpl implements FinancialDeficitManagement {

	final static Logger logger = Logger.getLogger("FinancialDeficitManagementImpl");

	private final FinancialDeficitService financialDeficitService;
	private final PullAccountService pullAccountService;
	private final FinancialDeficitMapper financialDeficitMapper;
	private final CryptoMngrCoreService cryptoMngrCoreService;

	@Value("${attachment.deficit}")
	private String deficitBasePackagePath;

	@Autowired
	public FinancialDeficitManagementImpl(FinancialDeficitService financialDeficitService,
			CryptoMngrCoreService cryptoMngrCoreService, FinancialDeficitMapper financialDeficitMapper,
			PullAccountService pullAccountService) {
		super();
		this.financialDeficitService = financialDeficitService;
		this.cryptoMngrCoreService = cryptoMngrCoreService;
		this.financialDeficitMapper = financialDeficitMapper;
		this.pullAccountService = pullAccountService;
	}

	@Override
	public FinancialDeficit createFinancialDeficit(PullAccount pullAccount, double deficitAmount) {

		FinancialDeficit financialDeficitObj = new FinancialDeficit();

		financialDeficitObj.setDeficitAmount(deficitAmount);
		financialDeficitObj.setStatusFk(StatusConstant.STATUS_NEW);
		financialDeficitObj.setCreatedAt(Date.from(Instant.now()));
		financialDeficitObj.setCreatedBy(pullAccount.getUpdatedBy());
		financialDeficitObj.setPullAccountFk(pullAccount);
		financialDeficitObj.setPosId(pullAccount.getPosId());
		financialDeficitObj.setServiceId(pullAccount.getServiceId());

		return financialDeficitService.save(financialDeficitObj);

	}

	@Override
	public FinancialDeficit saveFinancialDeficit(FinancialDeficit financialDeficit) {
		return financialDeficitService.save(financialDeficit);
	}

	@Override
	public List<FinancialDeficitDTO> getNewOrRejectedFinancialDeficit(
			FinancialDeficitByUserRequest financialDeficitByUserRequest) {

		return financialDeficitMapper.financialDeficitsToFinancialDeficitDTOs(
				financialDeficitService.findByCreatedByAndStatusFk(financialDeficitByUserRequest.getUserName(),
						StatusConstant.STATUS_NEW, StatusConstant.STATUS_REJECTED));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public FinancialDeficitDTO closeFinancialDeficit(CloseFinancialDeficitRequest closeFinancialDeficitRequest) {

		FinancialDeficit eFinancialDeficit = financialDeficitService
				.findById(Long.valueOf(closeFinancialDeficitRequest.getFinancialDeficitId())).get();

		String url = financialDeficitService.saveAttachedFile(closeFinancialDeficitRequest.getFileAtt(),
				eFinancialDeficit.getPullAccountFk().getSettlementCode(), deficitBasePackagePath);

		eFinancialDeficit.setAttUrl(url);
		eFinancialDeficit.setDeficitAmount(Double.valueOf(closeFinancialDeficitRequest.getAmount()));
		eFinancialDeficit.setId(Long.valueOf(closeFinancialDeficitRequest.getFinancialDeficitId()));
		eFinancialDeficit.setStatusFk(StatusConstant.STATUS_PENDING);
		eFinancialDeficit.setUpdatedAt(Date.from(Instant.now()));
		eFinancialDeficit.setUpdatedBy(closeFinancialDeficitRequest.getUsername());

		PullAccount pullAccount = eFinancialDeficit.getPullAccountFk();
		pullAccount.setDeficitStatus(StatusConstant.STATUS_CLOSED);
		pullAccount.setDepositTotalAmount(pullAccount.getDepositTotalAmount() + eFinancialDeficit.getDeficitAmount());
		pullAccount.setDepositCash(pullAccount.getDepositCash() + eFinancialDeficit.getDeficitAmount());
//		pullAccount.setDeficitAmount(pullAccount.getDeficitAmount() - eFinancialDeficit.getDeficitAmount());
		pullAccount.setUpdatedAt(Date.from(Instant.now()));
		pullAccount.setUpdatedBy(closeFinancialDeficitRequest.getUsername());
		pullAccountService.save(pullAccount);

		return financialDeficitMapper
				.financialDeficitToFinancialDeficitDTO(financialDeficitService.save(eFinancialDeficit));
	}

	@Override
	public InputStream downloadFinancialDeficitAttFile(URLRequest uRLRequest) {
		File pdfFile = new File(uRLRequest.getUrl());
		InputStream is = null;
		try {
			is = new FileInputStream(pdfFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return is;
	}

	@Override
	public FinancialDeficitPagingDTO getApprovedFinancialDeficitByUser(
			ApprovedFinancialDeficitRequest approvedFinancialDeficitRequest) {

		Page<FinancialDeficit> pageResult = financialDeficitService.findByStatusFkAndCreatedBy(
				approvedFinancialDeficitRequest, StatusConstant.STATUS_APPROVED,
				approvedFinancialDeficitRequest.getUsername());

		FinancialDeficitPagingDTO financialDeficitPagingDTO = new FinancialDeficitPagingDTO();
		financialDeficitPagingDTO.setCount(pageResult.getTotalElements());
		financialDeficitPagingDTO.setTotalPages(pageResult.getTotalPages());
		
		List<String> eTransaction = new ArrayList<String>();
		financialDeficitMapper.financialDeficitsToFinancialDeficitDTOs(pageResult.getContent()).stream().forEach(s -> {
			eTransaction.add(cryptoMngrCoreService.encrypt(s.toString()));
		});
		
		financialDeficitPagingDTO.setTransactions(eTransaction);

		return financialDeficitPagingDTO;
	}

	@Override
	public FinancialDeficit findByPullAccountFk(PullAccount pullAccount) {
		return financialDeficitService.findByPullAccountFk(pullAccount);
	}

	@Override
	public void deleteFinancialDeficit(FinancialDeficit financialDeficitFk) {
		financialDeficitService.deleteById(financialDeficitFk.getId());
	}

}
