package com.aman.payment.core.management;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.aman.payment.auth.model.CustomUserDetails;
import com.aman.payment.core.mapper.FinancialDeficitMapper;
import com.aman.payment.core.mapper.PullAccountMapper;
import com.aman.payment.core.model.FinancialDeficit;
import com.aman.payment.core.model.PullAccount;
import com.aman.payment.core.model.dto.FinancialDeficitDTO;
import com.aman.payment.core.model.dto.FinancialDeficitPagingDTO;
import com.aman.payment.core.model.dto.SettlementDTO;
import com.aman.payment.core.model.dto.SettlementPagingDTO;
import com.aman.payment.core.model.payload.ApprovedClaimsSearchRequest;
import com.aman.payment.core.model.payload.ApprovedFinancialDeficitRequest;
import com.aman.payment.core.model.payload.ApprovedPullAccountRequest;
import com.aman.payment.core.model.payload.ReviewFinancialDeficitRequest;
import com.aman.payment.core.model.payload.ReviewSettlementRequest;
import com.aman.payment.core.model.payload.SettlementByPosIdsRequest;
import com.aman.payment.core.model.payload.URLRequest;
import com.aman.payment.core.service.CryptoMngrCoreService;
import com.aman.payment.core.service.FinancialDeficitService;
import com.aman.payment.core.service.PullAccountService;
import com.aman.payment.util.StatusConstant;

@Component
public class SettlementManagementImpl extends ProcessCodeImp implements SettlementManagement {

	private final FinancialDeficitService financialDeficitService;
	private final PullAccountService pullAccountService;
	private final FinancialDeficitMapper financialDeficitMapper;
	private final PullAccountMapper pullAccountMapper;
	private final CryptoMngrCoreService cryptoMngrCoreService;

	@Autowired
	public SettlementManagementImpl(PullAccountService pullAccountService,
			FinancialDeficitService financialDeficitService, PullAccountMapper pullAccountMapper,
			FinancialDeficitMapper financialDeficitMapper, CryptoMngrCoreService cryptoMngrCoreService) {
		super();
		this.pullAccountService = pullAccountService;
		this.financialDeficitService = financialDeficitService;
		this.pullAccountMapper = pullAccountMapper;
		this.financialDeficitMapper = financialDeficitMapper;
		this.cryptoMngrCoreService = cryptoMngrCoreService;
	}

	@Override
	public List<FinancialDeficitDTO> getNotApprovedFinancialDeficit(
			SettlementByPosIdsRequest settlementByPosIdsRequest) {

		Set<Long> ePosIds = new HashSet<Long>();
		settlementByPosIdsRequest.getPosIds().stream().forEach(s -> {
			ePosIds.add(Long.valueOf(s));
		});

		return financialDeficitMapper.financialDeficitsToFinancialDeficitDTOs(
				financialDeficitService.findByStatusFkNotAndPosIn(ePosIds, StatusConstant.STATUS_APPROVED));
	}

	@Override
	public FinancialDeficitPagingDTO getApprovedFinancialDeficit(
			ApprovedFinancialDeficitRequest approvedFinancialDeficitRequest) {

		Set<Long> ePosIds = new HashSet<Long>();
		approvedFinancialDeficitRequest.getPosIds().stream().forEach(s -> {
			ePosIds.add(Long.valueOf(s));
		});

		Page<FinancialDeficit> pageResult = financialDeficitService
				.findByApprovedBy(approvedFinancialDeficitRequest);

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
	@Transactional(rollbackFor = Exception.class)
	public FinancialDeficitDTO reviewFinancialDeficit(ReviewFinancialDeficitRequest reviewFinancialDeficitRequest) {

		FinancialDeficit eFinancialDeficit = financialDeficitService
				.findById(Long.valueOf(reviewFinancialDeficitRequest.getFinancialDeficitId())).get();

		PullAccount ePullAccount = eFinancialDeficit.getPullAccountFk();

		Date approvedAt = Date.from(Instant.now());

		eFinancialDeficit.setApprovedAt(approvedAt);
		eFinancialDeficit.setApprovedBy(reviewFinancialDeficitRequest.getUserName());
		eFinancialDeficit.setApprovedComment(reviewFinancialDeficitRequest.getComment());
		eFinancialDeficit.setStatusFk(reviewFinancialDeficitRequest.getStatus());
		eFinancialDeficit.setUpdatedAt(approvedAt);
		eFinancialDeficit.setUpdatedBy(reviewFinancialDeficitRequest.getUserName());

		eFinancialDeficit = financialDeficitService.save(eFinancialDeficit);

		if (reviewFinancialDeficitRequest.getStatus().equals(StatusConstant.STATUS_APPROVED)) {
			ePullAccount.setDeficitStatus(StatusConstant.STATUS_CLOSED);
			pullAccountService.save(ePullAccount);
		}

		return financialDeficitMapper.financialDeficitToFinancialDeficitDTO(eFinancialDeficit);
	}

	@Override
	public List<SettlementDTO> getNotApprovedPullAccount(SettlementByPosIdsRequest settlementByPosIdsRequest) {

		Set<Long> ePosIds = new HashSet<Long>();
		settlementByPosIdsRequest.getPosIds().stream().forEach(s -> {
			ePosIds.add(Long.valueOf(s));
		});

		return pullAccountMapper.pullAccountsToSettlementDTOs(
				pullAccountService.findByStatusFkNotAndPosIn(ePosIds, StatusConstant.STATUS_APPROVED));
	}

	@Override
	public SettlementPagingDTO getApprovedPullAccount(ApprovedPullAccountRequest approvedPullAccountRequest, CustomUserDetails customUserDetails) {
		Page<PullAccount> pageResult;
		
		if(customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_FINANCE) ||
				customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_SUPPORT)) {
		     pageResult = pullAccountService.findAllApprovedRequests(approvedPullAccountRequest);

		}
		else {
			 pageResult = pullAccountService.findByApprovedBy(approvedPullAccountRequest);
		}
		SettlementPagingDTO settlementPagingDTO = new SettlementPagingDTO();
		settlementPagingDTO.setCount(pageResult.getTotalElements());
		settlementPagingDTO.setTotalPages(pageResult.getTotalPages());
		
		List<String> eTransaction = new ArrayList<String>();
		pullAccountMapper.pullAccountsToSettlementDTOs(pageResult.getContent()).stream().forEach(s -> {
			eTransaction.add(cryptoMngrCoreService.encrypt(s.toString()));
		});
		
		settlementPagingDTO.setTransactions(eTransaction);

		return settlementPagingDTO;
	}

	@Override
	public SettlementDTO reviewSettlement(ReviewSettlementRequest reviewSettlementRequest) {

		PullAccount ePullAccount = pullAccountService.findById(Long.valueOf(reviewSettlementRequest.getPullAccountId()))
				.get();

		Date approvedAt = Date.from(Instant.now());

		if (reviewSettlementRequest.getStatus().equals(StatusConstant.STATUS_REJECTED)) {
			
			FinancialDeficit financialDeficitFk = financialDeficitService.findByPullAccountFk(ePullAccount);
			if(financialDeficitFk != null)
				financialDeficitService.deleteById(financialDeficitFk.getId());
			
			ePullAccount = rejectedPullAccount(ePullAccount);
		}

		ePullAccount.setApprovedAt(approvedAt);
		ePullAccount.setApprovedBy(reviewSettlementRequest.getUserName());
		ePullAccount.setApprovedComment(reviewSettlementRequest.getComment());
		ePullAccount.setStatusFk(reviewSettlementRequest.getStatus());
		ePullAccount.setUpdatedAt(approvedAt);
		ePullAccount.setUpdatedBy(reviewSettlementRequest.getUserName());

		return pullAccountMapper.pullAccountToSettlementDTO(pullAccountService.save(ePullAccount));
	}
	
	private PullAccount rejectedPullAccount(PullAccount ePullAccount) {
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

	@Override
	public SettlementPagingDTO getApprovedPullAccountSearch(ApprovedClaimsSearchRequest approvedClaimsSearchRequest) {
		if(approvedClaimsSearchRequest.getPosId() != null && !approvedClaimsSearchRequest.getPosId().isEmpty()) {
			Set<String> posSet = approvedClaimsSearchRequest.getPosIds();
			posSet.clear();
			posSet.add(approvedClaimsSearchRequest.getPosId());
		}
		// TODO Auto-generated method stub
		Page<PullAccount> pageResult = pullAccountService.findApprovedClaimsAdvancedSearch(approvedClaimsSearchRequest);
		SettlementPagingDTO settlementPagingDTO = new SettlementPagingDTO();
		settlementPagingDTO.setCount(pageResult.getTotalElements());
		settlementPagingDTO.setTotalPages(pageResult.getTotalPages());
		
		List<String> eTransaction = new ArrayList<String>();
		pullAccountMapper.pullAccountsToSettlementDTOs(pageResult.getContent()).stream().forEach(s -> {
			eTransaction.add(cryptoMngrCoreService.encrypt(s.toString()));
		});
		
		settlementPagingDTO.setTransactions(eTransaction);

		return settlementPagingDTO;
		 
	}

}
