package com.aman.payment.core.management;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.aman.payment.core.mapper.PullAccountMapper;
import com.aman.payment.core.model.FinancialDeficit;
import com.aman.payment.core.model.PullAccount;
import com.aman.payment.core.model.dto.PullAccountCalculationDTO;
import com.aman.payment.core.model.dto.PullAccountDTO;
import com.aman.payment.core.model.dto.PullAccountPagingDTO;
import com.aman.payment.core.model.payload.ApprovedPullAccountRequest;
import com.aman.payment.core.model.payload.CloseDayPullAccountRequest;
import com.aman.payment.core.model.payload.GetPullAccountRequest;
import com.aman.payment.core.model.payload.OpeningPullAccountRequest;
import com.aman.payment.core.model.payload.PullAccountByUserRequest;
import com.aman.payment.core.model.payload.URLRequest;
import com.aman.payment.core.service.CryptoMngrCoreService;
import com.aman.payment.core.service.PullAccountService;
import com.aman.payment.util.StatusConstant;

@Component
public class PullAccountManagementImpl extends ProcessCodeImp implements PullAccountManagement{

	final static Logger logger = Logger.getLogger("PullAccountManagementImpl");

	private final PullAccountService pullAccountService;
	private final PullAccountMapper pullAccountMapper;
	private final FinancialDeficitManagement financialDeficitManagement;
	private final CryptoMngrCoreService cryptoMngrCoreService;

	@Value("${attachment.deposit.bank}")
	private String bankBasePackagePath;

	@Value("${attachment.deposit.visa}")
	private String visaBasePackagePath;

	@Autowired
	public PullAccountManagementImpl(PullAccountService pullAccountService,
			PullAccountMapper pullAccountMapper, FinancialDeficitManagement financialDeficitManagement,
			CryptoMngrCoreService cryptoMngrCoreService) {
		super();
		this.pullAccountService = pullAccountService;
		this.pullAccountMapper = pullAccountMapper;
		this.financialDeficitManagement = financialDeficitManagement;
		this.cryptoMngrCoreService = cryptoMngrCoreService;
	}

	@Override
	public Optional<PullAccount> validatePullAccount(String createdBy, String settlementCode, long serviceId) {
		return pullAccountService.findByStatusFkAndCreatedByAndSettlementCodeAndServiceId(
				StatusConstant.STATUS_NEW, createdBy, settlementCode, serviceId);
	}
	
	@Override
	public Optional<PullAccount> validatePullAccount(String settlementCode, long serviceId) {
		return pullAccountService.findBySettlementCodeAndServiceId(settlementCode, serviceId);
	}

	@Override
	public PullAccount savePullAccount(PullAccount pullAccount) {
		return pullAccountService.save(pullAccount);
	}

	@Override
	public List<PullAccountDTO> getNewOrRejectedPullAccount(PullAccountByUserRequest pullAccountByUserRequest) {

		return pullAccountMapper.pullAccountsToPullAccountDTOs(pullAccountService.getNewOrRejectedPullAccountByUser(
				pullAccountByUserRequest.getUsername(), StatusConstant.STATUS_NEW, StatusConstant.STATUS_REJECTED));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public PullAccountDTO closeDayPullAccount(CloseDayPullAccountRequest closeDayPullAccountRequest) {

		PullAccount ePullAccount = pullAccountService
				.findById(Long.valueOf(closeDayPullAccountRequest.getPullAccountId())).get();
		
		ePullAccount.setUpdatedAt(Date.from(Instant.now()));
		ePullAccount.setUpdatedBy(closeDayPullAccountRequest.getUserName());
		
		ePullAccount.setDepositCash(Double.valueOf(closeDayPullAccountRequest.getAmountCash()));
		ePullAccount.setDepositVisa(Double.valueOf(closeDayPullAccountRequest.getAmountVisa()));
		double depositTotalAmount = Double.valueOf(closeDayPullAccountRequest.getAmountCash())
				+ Double.valueOf(closeDayPullAccountRequest.getAmountVisa());
		ePullAccount.setDepositTotalAmount(depositTotalAmount);

//		ePullAccount.setAttBankUrl(pullAccountService.saveAttachedFile(closeDayPullAccountRequest.getFileBankAtt(),
//				closeDayPullAccountRequest.getSettlementCode(), "Bank", bankBasePackagePath));
		ePullAccount.setAttVisaUrl(pullAccountService.saveAttachedFile(closeDayPullAccountRequest.getFileVisaAtt(),
				closeDayPullAccountRequest.getSettlementCode(), "Visa", visaBasePackagePath));

		PullAccountCalculationDTO dPullAccountCalculationDTO = calculatePullAccount(ePullAccount.getTransactions());

		ePullAccount.setTotalRefundTransaction(dPullAccountCalculationDTO.getTotalRefundTransactions());
		ePullAccount.setSystemAmountRefund(dPullAccountCalculationDTO.getTotalRefundAmount());
		
		ePullAccount.setTotalCancelledTransaction(dPullAccountCalculationDTO.getTotalCancelledTransactions());
		ePullAccount.setSystemTotalCancelledAmount(dPullAccountCalculationDTO.getTotalCancelledAmount());
		
		ePullAccount.setTotalSettlementTransaction(dPullAccountCalculationDTO.getTotalSettlementTransactions());
		ePullAccount.setSystemTotalSettlemetAmount(dPullAccountCalculationDTO.getTotalSettlementAmount());
		
		ePullAccount.setTotalCollectionTransaction(dPullAccountCalculationDTO.getTotalCollectionTransactions());
		ePullAccount.setSystemTotalCollectionAmount(dPullAccountCalculationDTO.getTotalCollectionAmount());
		
		ePullAccount.setTotalTransaction(dPullAccountCalculationDTO.getTotalTransactions());
		
//		ePullAccount.setSystemAmountVisa(dPullAccountCalculationDTO.getTotalSettlementAmountVisa());
//		ePullAccount.setSystemAmountCash(dPullAccountCalculationDTO.getTotalSettlementAmountCash());
//		ePullAccount.setSystemTotalAmount(dPullAccountCalculationDTO.getTotalSettlementAmount());
		
		ePullAccount.setSystemAmountVisa(dPullAccountCalculationDTO.getTotalCollectionAmountVisa());
		ePullAccount.setSystemAmountCash(dPullAccountCalculationDTO.getTotalCollectionAmountCash());
		
		ePullAccount.setSystemTotalAmount(dPullAccountCalculationDTO.getTotalTransactionAmount());

		ePullAccount.setIsDeficit(false);
		ePullAccount.setDeficitAmount(0);
		ePullAccount.setIsOver(false);
		ePullAccount.setOverAmount(0);
		
		if (depositTotalAmount < dPullAccountCalculationDTO.getTotalCollectionAmount()) {
			Double deficitAmount = dPullAccountCalculationDTO.getTotalCollectionAmount() - depositTotalAmount;
			ePullAccount.setIsDeficit(true);
			ePullAccount.setDeficitAmount(deficitAmount);
			ePullAccount.setDeficitStatus(StatusConstant.STATUS_OPEN);
			ePullAccount.setIsOver(false);
			ePullAccount.setOverAmount(0);
			ePullAccount.setPosId(ePullAccount.getPosId());

			financialDeficitManagement.createFinancialDeficit(ePullAccount, deficitAmount);

		} else if (depositTotalAmount > dPullAccountCalculationDTO.getTotalCollectionAmount()) {
			Double overAmount = depositTotalAmount - dPullAccountCalculationDTO.getTotalCollectionAmount();
			ePullAccount.setIsDeficit(false);
			ePullAccount.setDeficitAmount(0);
			ePullAccount.setIsOver(true);
			ePullAccount.setOverAmount(overAmount);

		}

		ePullAccount.setStatusFk(StatusConstant.STATUS_PENDING);
		return pullAccountMapper.pullAccountToPullAccountDTO(pullAccountService.save(ePullAccount));
	}

	@Override
	public InputStream downloadPullAccountAttFile(URLRequest uRLRequest) {
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
	public PullAccountPagingDTO approvedPullAccountByUser(ApprovedPullAccountRequest approvedPullAccountRequest) {
		Page<PullAccount> pageResult = pullAccountService.findByStatusFkAndCreatedBy(approvedPullAccountRequest,
				StatusConstant.STATUS_APPROVED, approvedPullAccountRequest.getUsername());

		PullAccountPagingDTO pullAccountPagingDTO = new PullAccountPagingDTO();
		pullAccountPagingDTO.setCount(pageResult.getTotalElements());
		pullAccountPagingDTO.setTotalPages(pageResult.getTotalPages());
		
		List<String> eTransaction = new ArrayList<String>();
		pullAccountMapper.pullAccountsToPullAccountDTOs(pageResult.getContent()).stream().forEach(s -> {
			eTransaction.add(cryptoMngrCoreService.encrypt(s.toString()));
		});
		
		pullAccountPagingDTO.setTransactions(eTransaction);

		return pullAccountPagingDTO;
	}

	@Override
	public List<String> openingPullAccountByUser(OpeningPullAccountRequest openingPullAccountRequest) {
		List<PullAccount> result = pullAccountService.
				findByStatusFkAndServiceId(StatusConstant.STATUS_NEW, 
						Long.valueOf(openingPullAccountRequest.getServiceId()));
		
		List<String> eSettlementCode = new ArrayList<String>();
		result.stream().forEach(s -> {
			eSettlementCode.add(cryptoMngrCoreService.encrypt(s.getSettlementCode()));
		});
		
		return eSettlementCode;
	}


	@Override
	public List<String> openingPullAccount() {
		List<PullAccount> result = pullAccountService.
				findByStatusFk(StatusConstant.STATUS_NEW);
		
		List<String> eSettlementCode = new ArrayList<String>();
		pullAccountMapper.pullAccountsToPullAccountDTOs(result).stream().forEach(s -> {
			eSettlementCode.add(cryptoMngrCoreService.encrypt(s.toString()));
		});
		
		return eSettlementCode;
	}

	@Override
	public String getSettlementCode(GetPullAccountRequest decryptGetPullAccountRequest) {

		JSONObject jsonObject = new JSONObject();
		
		Optional<PullAccount> pullAccountOptional = pullAccountService.getSettlementCode(decryptGetPullAccountRequest);
		
		if(pullAccountOptional.isPresent()) {
			
			return cryptoMngrCoreService.encrypt(pullAccountMapper.
					pullAccountToPullAccountDTO(pullAccountOptional.get()).toString());
			
		}else {
			
			jsonObject.put("response", "Sorry, This Settlement Code not Exist...");
			return cryptoMngrCoreService.encrypt(jsonObject.toString());
			
		}
		
	}

	@Override
	public String updateSettlementCode(GetPullAccountRequest decryptGetPullAccountRequest) {
		
		JSONObject jsonObject = new JSONObject();
		
		Optional<PullAccount> pullAccountOptional = pullAccountService.findById(decryptGetPullAccountRequest.getPullAccountId());
		if(pullAccountOptional.isPresent()) {
			
			PullAccount ePullAccount = pullAccountOptional.get();
			PullAccount newPullAccount = resetPullAccount(ePullAccount);
			pullAccountService.save(newPullAccount);
			
			FinancialDeficit financialDeficitFk = financialDeficitManagement.findByPullAccountFk(ePullAccount);
			if(financialDeficitFk != null)
				financialDeficitManagement.deleteFinancialDeficit(financialDeficitFk);
			
			return cryptoMngrCoreService.encrypt(pullAccountMapper.
					pullAccountToPullAccountDTO(newPullAccount).toString());
			
		}else {
			
			jsonObject.put("response", "Sorry, This Settlement not Exist...");
			return cryptoMngrCoreService.encrypt(jsonObject.toString());
			
		}
	}
	
	private PullAccount resetPullAccount(PullAccount ePullAccount) {
		ePullAccount.setStatusFk(StatusConstant.STATUS_NEW);
		ePullAccount.setDeficitAmount(0);
		ePullAccount.setDeficitStatus(null);
		ePullAccount.setDepositCash(0);
		ePullAccount.setDepositTotalAmount(0);
		ePullAccount.setDepositVisa(0);
		ePullAccount.setIsDeficit(false);
		ePullAccount.setIsOver(false);
		ePullAccount.setOverAmount(0);
		ePullAccount.setSystemTotalSettlementAmount(0);
		
		ePullAccount.setTotalRefundTransaction(0);
		ePullAccount.setSystemAmountRefund(0);
		ePullAccount.setTotalCancelledTransaction(0);
		ePullAccount.setSystemTotalCancelledAmount(0);
		ePullAccount.setTotalSettlementTransaction(0);
		ePullAccount.setSystemTotalSettlemetAmount(0);
		ePullAccount.setTotalCollectionTransaction(0);
		ePullAccount.setSystemTotalCollectionAmount(0);
		ePullAccount.setTotalTransaction(0);
		ePullAccount.setSystemAmountVisa(0);
		ePullAccount.setSystemAmountCash(0);
		ePullAccount.setSystemTotalAmount(0);
		return ePullAccount;
	}

	
}
