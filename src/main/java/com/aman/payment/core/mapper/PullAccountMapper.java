package com.aman.payment.core.mapper;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.aman.payment.core.management.ProcessCodeImp;
import com.aman.payment.core.model.PullAccount;
import com.aman.payment.core.model.Transaction;
import com.aman.payment.core.model.dto.FinancialAuditDTO;
import com.aman.payment.core.model.dto.PullAccountCalculationDTO;
import com.aman.payment.core.model.dto.PullAccountDTO;
import com.aman.payment.core.model.dto.SettlementDTO;
import com.aman.payment.core.util.UtilCore;
import com.aman.payment.util.StatusConstant;

@Service
public class PullAccountMapper extends ProcessCodeImp{

	/*
	 * pullAccount to pullAccountDTO pullAccounts to pullAccountDTOs
	 * *****************************************************************************
	 * ***********************
	 */
	public PullAccountDTO pullAccountToPullAccountDTO(PullAccount pullAccount) {
		return createPullAccountDTO(pullAccount);
	}

	public List<PullAccountDTO> pullAccountsToPullAccountDTOs(List<PullAccount> pullAccounts) {
		return pullAccounts.stream().filter(Objects::nonNull).map(this::pullAccountToPullAccountDTO)
				.collect(Collectors.toList());
	}

	public Set<PullAccountDTO> pullAccountsToPullAccountDTOs(Set<PullAccount> pullAccounts) {
		return pullAccounts.stream().filter(Objects::nonNull).map(this::pullAccountToPullAccountDTO)
				.collect(Collectors.toSet());
	}

	private PullAccountDTO createPullAccountDTO(PullAccount pullAccount) {
		PullAccountDTO pullAccountDTO = new PullAccountDTO();

		if (pullAccount.getStatusFk().equals(StatusConstant.STATUS_NEW)
				|| pullAccount.getStatusFk().equals(StatusConstant.STATUS_REJECTED)) {

			Set<Transaction> transactionSet = pullAccount.getTransactions();

			PullAccountCalculationDTO pullAccountCalculation = calculatePullAccount(transactionSet);

			pullAccountDTO
					.setSysTotalTransactions(String.valueOf(pullAccountCalculation.getTotalTransactions()));
			pullAccountDTO.setSysTotalTransactionsAmount(String.format("%.2f", pullAccountCalculation.getTotalTransactionAmount()));
			pullAccountDTO.setSysTotalCollectionTransactions(
					String.valueOf(pullAccountCalculation.getTotalCollectionTransactions()));
			pullAccountDTO
					.setSysTotalRefundTransactions(String.valueOf(pullAccountCalculation.getTotalRefundTransactions()));
			pullAccountDTO.setSysTotalSettlementTransaction(
					String.valueOf(pullAccountCalculation.getTotalSettlementTransactions()));
			pullAccountDTO.setSysTotalCancelledTransactions(
					String.valueOf(pullAccountCalculation.getTotalCancelledTransactions()));


			pullAccountDTO.setSysTotalCollectionAmount(String.format("%.2f", pullAccountCalculation.getTotalCollectionAmount()));
			pullAccountDTO.setSysAmountRefund(String.format("%.2f", pullAccountCalculation.getTotalRefundAmount()));
			pullAccountDTO
					.setSysTotalSettlemetAmount(String.format("%.2f", pullAccountCalculation.getTotalSettlementAmount()));

			pullAccountDTO.setSysSettlementAmountCash(String.format("%.2f", pullAccountCalculation.getTotalSettlementAmountCash()));
			pullAccountDTO.setSysSettlementAmountVisa(String.format("%.2f", pullAccountCalculation.getTotalSettlementAmountVisa()));
			
			pullAccountDTO.setSysAmountCash(String.format("%.2f", pullAccountCalculation.getTotalCollectionAmountCash()));
			pullAccountDTO.setSysAmountVisa(String.format("%.2f", pullAccountCalculation.getTotalCollectionAmountVisa()));
			pullAccountDTO.setSysTotalCancelledAmount(String.format("%.2f", pullAccountCalculation.getTotalCancelledAmount()));
			
//			pullAccountDTO.setSysTotalDepositTransactions(String.valueOf(pullAccountCalculation.getTotalDepositTransactions()));
//			pullAccountDTO.setSysTotalDepositAmount(String.valueOf(pullAccountCalculation.getTotalDepositAmount()));
//			pullAccountDTO.setSysTotalDepositAmountCash(String.valueOf(pullAccountCalculation.getTotalCollectionAmountCash() - pullAccountCalculation.getTotalRefundAmountCash()));
//			pullAccountDTO.setSysTotalDepositAmountVisa(String.valueOf(pullAccountCalculation.getTotalCollectionAmountVisa() - pullAccountCalculation.getTotalRefundAmountVisa()));

		} else {

			pullAccountDTO.setSysTotalTransactions(String.valueOf(pullAccount.getTotalTransaction()));
			pullAccountDTO.setSysTotalTransactionsAmount(String.format("%.2f", pullAccount.getSystemTotalAmount()));
			
			pullAccountDTO.setSysTotalCancelledTransactions(
					String.valueOf(pullAccount.getTotalCancelledTransaction()));
			pullAccountDTO
					.setSysTotalCollectionTransactions(String.valueOf(pullAccount.getTotalCollectionTransaction()));
			pullAccountDTO.setSysTotalRefundTransactions(String.valueOf(pullAccount.getTotalRefundTransaction()));
			pullAccountDTO
					.setSysTotalSettlementTransaction(String.valueOf(pullAccount.getTotalSettlementTransaction()));

			pullAccountDTO.setSysTotalCollectionAmount(String.format("%.2f", pullAccount.getSystemTotalCollectionAmount()));
			pullAccountDTO.setSysAmountRefund(String.format("%.2f", pullAccount.getSystemAmountRefund()));
			pullAccountDTO.setSysTotalSettlemetAmount(String.format("%.2f", pullAccount.getSystemTotalSettlemetAmount()));


			pullAccountDTO.setSysSettlementAmountCash(String.format("%.2f", pullAccount.getSystemAmountCash()));
			pullAccountDTO.setSysSettlementAmountVisa(String.format("%.2f", pullAccount.getSystemAmountVisa()));
			pullAccountDTO.setSysAmountCash(String.format("%.2f", pullAccount.getSystemAmountCash()));
			pullAccountDTO.setSysAmountVisa(String.format("%.2f", pullAccount.getSystemAmountVisa()));
			pullAccountDTO.setSysTotalCancelledAmount(String.format("%.2f", pullAccount.getSystemTotalCancelledAmount()));

//			pullAccountDTO.setSysTotalDepositTransactions(String.valueOf(pullAccount.getTotalCollectionTransaction() - pullAccount.getTotalRefundTransaction()));
//			pullAccountDTO.setSysTotalDepositAmount(String.valueOf(pullAccount.getSystemTotalCollectionAmount() - pullAccount.getSystemAmountRefund()));
//			pullAccountDTO.setSysTotalDepositAmountCash(String.valueOf(pullAccount.getSystemAmountCash()));
//			pullAccountDTO.setSysTotalDepositAmountVisa(String.valueOf(pullAccount.getSystemAmountVisa()));

		}
		pullAccountDTO.setComment(pullAccount.getComment());
		pullAccountDTO.setTotalDeposit(String.format("%.2f", pullAccount.getDepositTotalAmount()));
		pullAccountDTO.setTotalDepositCash(String.format("%.2f", pullAccount.getDepositCash()));
		pullAccountDTO.setTotalDepositVisa(String.format("%.2f", pullAccount.getDepositVisa()));

		pullAccountDTO.setCreatedAt(UtilCore.changeDateFormat(pullAccount.getCreatedAt(), "dd/MM/yyyy"));
		pullAccountDTO.setCreatedBy(pullAccount.getCreatedBy());
		pullAccountDTO.setId(String.valueOf(pullAccount.getId()));
		pullAccountDTO.setLocation(pullAccount.getLocationName());
		pullAccountDTO.setSettlementCode(pullAccount.getSettlementCode());
		pullAccountDTO.setStatus(pullAccount.getStatusFk());

		pullAccountDTO.setApprovedAt(pullAccount.getApprovedAt() != null
				? UtilCore.changeDateFormat(pullAccount.getApprovedAt(), "dd/MM/yyyy")
				: "");
		pullAccountDTO.setApprovedBy(pullAccount.getApprovedBy());
		pullAccountDTO.setApprovedComment(pullAccount.getApprovedComment());
		pullAccountDTO.setServiceId(String.valueOf(pullAccount.getServiceId()));

		return pullAccountDTO;
	}

	/*
	 * pullAccount to pullAccountDTO pullAccounts to pullAccountDTOs
	 * *****************************************************************************
	 * ***********************
	 */
	public SettlementDTO pullAccountToSettlementDTO(PullAccount pullAccount) {
		return createSettlementDTO(pullAccount);
	}

	public List<SettlementDTO> pullAccountsToSettlementDTOs(List<PullAccount> pullAccounts) {
		return pullAccounts.stream().filter(Objects::nonNull).map(this::pullAccountToSettlementDTO)
				.collect(Collectors.toList());
	}

	public Set<SettlementDTO> pullAccountsToSettlementDTOs(Set<PullAccount> pullAccounts) {
		return pullAccounts.stream().filter(Objects::nonNull).map(this::pullAccountToSettlementDTO)
				.collect(Collectors.toSet());
	}

	private SettlementDTO createSettlementDTO(PullAccount pullAccount) {
		SettlementDTO settlementDTO = new SettlementDTO();

		settlementDTO.setTransactionDatetime(UtilCore.changeDateFormat(pullAccount.getCreatedAt(), "dd/MM/yyyy"));
		settlementDTO.setSettlementId(String.valueOf(pullAccount.getId()));
		settlementDTO.setLocationName(pullAccount.getLocationName());
		settlementDTO.setLocationCode(pullAccount.getLocationCode());
		settlementDTO.setPosId(String.valueOf(pullAccount.getPosId()));

		settlementDTO.setSettlementCode(pullAccount.getSettlementCode());
		settlementDTO.setStatus(pullAccount.getStatusFk());

		settlementDTO.setApprovedAt(pullAccount.getApprovedAt() != null
				? UtilCore.changeDateFormat(pullAccount.getApprovedAt(), "dd/MM/yyyy")
				: "");
		settlementDTO.setApprovedBy(pullAccount.getApprovedBy());
		settlementDTO.setApprovedComment(pullAccount.getApprovedComment());

		settlementDTO.setAgentName(pullAccount.getCreatedBy());
		settlementDTO.setAttBankUrl(pullAccount.getAttBankUrl());
		settlementDTO.setAttVisaUrl(pullAccount.getAttVisaUrl());
		settlementDTO.setComment(pullAccount.getComment());
		settlementDTO.setServiceId(String.valueOf(pullAccount.getServiceId()));
		
		if (pullAccount.getStatusFk().equals(StatusConstant.STATUS_NEW)
				|| pullAccount.getStatusFk().equals(StatusConstant.STATUS_REJECTED)) {

			PullAccountCalculationDTO pullAccountCalculation = calculatePullAccount(pullAccount.getTransactions());

			settlementDTO
					.setSysTotalTransactions(String.valueOf(pullAccountCalculation.getTotalTransactions()));
			settlementDTO.setSysTotalTransactionsAmount(String.format("%.2f", pullAccountCalculation.getTotalTransactionAmount()));
			settlementDTO.setSysTotalCollectionTransactions(
					String.valueOf(pullAccountCalculation.getTotalCollectionTransactions()));
			settlementDTO
					.setSysTotalRefundTransactions(String.valueOf(pullAccountCalculation.getTotalRefundTransactions()));
			settlementDTO.setSysTotalSettlementTransaction(
					String.valueOf(pullAccountCalculation.getTotalSettlementTransactions()));
			settlementDTO.setSysTotalCancelledTransactions(
					String.valueOf(pullAccountCalculation.getTotalCancelledTransactions()));

			settlementDTO.setSysTotalCollectionAmount(String.format("%.2f", pullAccountCalculation.getTotalCollectionAmount()));
			settlementDTO.setSysAmountRefund(String.format("%.2f", pullAccountCalculation.getTotalRefundAmount()));
			settlementDTO.setSysTotalSettlemetAmount(String.format("%.2f", pullAccountCalculation.getTotalSettlementAmount()));
			settlementDTO.setSysTotalCancelledAmount(String.format("%.2f", pullAccountCalculation.getTotalCancelledAmount()));

			settlementDTO.setSysAmountCash(String.format("%.2f", pullAccountCalculation.getTotalCollectionAmountCash()));
			settlementDTO.setSysAmountVisa(String.format("%.2f", pullAccountCalculation.getTotalCollectionAmountVisa()));
			
//			settlementDTO.setSysAmountCash(String.valueOf(pullAccountCalculation.getTotalCollectionAmountCash()));
//			settlementDTO.setSysAmountVisa(String.valueOf(pullAccountCalculation.getTotalCollectionAmountVisa()));
			
			settlementDTO.setSysTotalDepositTransactions(String.valueOf(pullAccountCalculation.getTotalDepositTransactions()));
			settlementDTO.setSysTotalDepositAmount(String.format("%.2f", pullAccountCalculation.getTotalDepositAmount()));
			settlementDTO.setSysTotalDepositAmountCash(String.format("%.2f", pullAccountCalculation.getTotalCollectionAmountCash() - pullAccountCalculation.getTotalRefundAmountCash()));
			settlementDTO.setSysTotalDepositAmountVisa(String.format("%.2f", pullAccountCalculation.getTotalCollectionAmountVisa() - pullAccountCalculation.getTotalRefundAmountVisa()));


		} else {

			settlementDTO.setSysTotalTransactions(String.valueOf(pullAccount.getTotalTransaction()));
			settlementDTO.setSysTotalTransactionsAmount(String.format("%.2f", pullAccount.getSystemTotalAmount()));
			settlementDTO.setSysTotalCancelledTransactions(
					String.valueOf(pullAccount.getTotalCancelledTransaction()));
			settlementDTO
					.setSysTotalCollectionTransactions(String.valueOf(pullAccount.getTotalCollectionTransaction()));
			settlementDTO.setSysTotalRefundTransactions(String.valueOf(pullAccount.getTotalRefundTransaction()));
			settlementDTO.setSysTotalSettlementTransaction(String.valueOf(pullAccount.getTotalSettlementTransaction()));

			settlementDTO.setSysTotalCollectionAmount(String.format("%.2f", pullAccount.getSystemTotalCollectionAmount()));
			settlementDTO.setSysAmountRefund(String.format("%.2f", pullAccount.getSystemAmountRefund()));
			settlementDTO.setSysTotalSettlemetAmount(String.format("%.2f", pullAccount.getSystemTotalSettlemetAmount()));
			settlementDTO.setSysTotalCancelledAmount(String.format("%.2f", pullAccount.getSystemTotalCancelledAmount()));

			settlementDTO.setSysAmountCash(String.format("%.2f", pullAccount.getSystemAmountCash()));
			settlementDTO.setSysAmountVisa(String.format("%.2f", pullAccount.getSystemAmountVisa()));

			settlementDTO.setTotalDeposit(String.format("%.2f", pullAccount.getDepositTotalAmount()));
			settlementDTO.setTotalDepositCash(String.format("%.2f", pullAccount.getDepositCash()));
			settlementDTO.setTotalDepositVisa(String.format("%.2f", pullAccount.getDepositVisa()));
			
			settlementDTO.setDeficitAmount(String.format("%.2f", pullAccount.getDeficitAmount()));
			settlementDTO.setDeficitStatus(pullAccount.getDeficitStatus());

			settlementDTO.setIsDeficit(String.valueOf(pullAccount.getIsDeficit()));
			settlementDTO.setIsOver(String.valueOf(pullAccount.getIsOver()));
			
			settlementDTO.setOverAmount(String.format("%.2f", pullAccount.getOverAmount()));
			
			if(pullAccount.getDeficitAmount() > 0) {
				settlementDTO.setOverDeficitAmount("-"+String.format("%.2f", pullAccount.getDeficitAmount()));
			}else if(pullAccount.getOverAmount() > 0){
				settlementDTO.setOverDeficitAmount("+"+String.format("%.2f", pullAccount.getOverAmount()));
			}
			
		}

//		settlementDTO.setSettlementApprovedStatus(pullAccount.get);

		return settlementDTO;
	}

	/*
	 * pullAccount to FinancialAuditDTODTO pullAccounts to pullAccountDTOs
	 * *****************************************************************************
	 * ***********************
	 */
	public FinancialAuditDTO pullAccountToFinancialAuditDTO(PullAccount pullAccount) {
		return createFinancialAuditDTO(pullAccount);
	}

	public List<FinancialAuditDTO> pullAccountsToFinancialAuditDTOs(List<PullAccount> pullAccounts) {
		return pullAccounts.stream().filter(Objects::nonNull).map(this::pullAccountToFinancialAuditDTO)
				.collect(Collectors.toList());
	}

	public Set<FinancialAuditDTO> pullAccountsToFinancialAuditDTOs(Set<PullAccount> pullAccounts) {
		return pullAccounts.stream().filter(Objects::nonNull).map(this::pullAccountToFinancialAuditDTO)
				.collect(Collectors.toSet());
	}

	private FinancialAuditDTO createFinancialAuditDTO(PullAccount pullAccount) {
		FinancialAuditDTO financialAuditDTO = new FinancialAuditDTO();
		
		financialAuditDTO.setAgentName(pullAccount.getCreatedBy());
		financialAuditDTO.setApprovedBy(pullAccount.getApprovedBy());
		financialAuditDTO.setApprovedDate(pullAccount.getApprovedAt()!=null?
				UtilCore.changeDateFormat(pullAccount.getApprovedAt(), "dd/MM/yyyy"):"");
		financialAuditDTO.setCreateDate(UtilCore.changeDateFormat(pullAccount.getCreatedAt(), "dd/MM/yyyy"));
		financialAuditDTO.setLocationCode(pullAccount.getLocationCode());
		financialAuditDTO.setLocationName(pullAccount.getLocationName());
		financialAuditDTO.setPosId(String.valueOf(pullAccount.getPosId()));
		financialAuditDTO.setSettlementCode(pullAccount.getSettlementCode());
		financialAuditDTO.setComment(pullAccount.getComment());
		financialAuditDTO.setServiceId(String.valueOf(pullAccount.getServiceId()));
		
//		if(pullAccount.getTotalCollectionTransaction() == 0) {
		if (pullAccount.getStatusFk().equals(StatusConstant.STATUS_NEW)
					|| pullAccount.getStatusFk().equals(StatusConstant.STATUS_REJECTED)) {
			
			PullAccountCalculationDTO pullAccountCalculation = calculatePullAccount(pullAccount.getTransactions());
			
			financialAuditDTO.setTotalTransaction(String.valueOf(pullAccountCalculation.getTotalTransactions()));
			financialAuditDTO.setSysTotalTransactionsAmount(String.format("%.2f", pullAccountCalculation.getTotalTransactionAmount()));
			
			financialAuditDTO.setTotalCollectionTransaction(String.valueOf(pullAccountCalculation.getTotalCollectionTransactions()));
			financialAuditDTO.setTotalRefundTransaction(String.valueOf(pullAccountCalculation.getTotalRefundTransactions()));
			financialAuditDTO.setTotalCancelledTransaction(String.valueOf(pullAccountCalculation.getTotalCancelledTransactions()));
			financialAuditDTO.setTotalSettlementTransaction(String.valueOf(pullAccountCalculation.getTotalSettlementTransactions()));
			
			financialAuditDTO.setSystemTotalCollectionAmount(String.format("%.2f", pullAccountCalculation.getTotalCollectionAmount()));
			financialAuditDTO.setSystemTotalSettlementAmount(String.format("%.2f", pullAccountCalculation.getTotalSettlementAmount()));
			financialAuditDTO.setSystemTotalCancelledAmount(String.format("%.2f", pullAccountCalculation.getTotalCancelledAmount()));
			financialAuditDTO.setSystemTotalAmountRefund(String.format("%.2f", pullAccountCalculation.getTotalRefundAmount()));
			
			financialAuditDTO.setSystemTotalAmountCash(String.format("%.2f", pullAccountCalculation.getTotalCollectionAmountCash()));
			financialAuditDTO.setSystemTotalAmountVisa(String.format("%.2f", pullAccountCalculation.getTotalCollectionAmountVisa()));	
			
		}else {
			
			financialAuditDTO.setTotalTransaction(String.valueOf(pullAccount.getTotalTransaction()));
			financialAuditDTO.setSysTotalTransactionsAmount(String.format("%.2f", pullAccount.getSystemTotalAmount()));
			
			financialAuditDTO.setTotalCollectionTransaction(String.valueOf(pullAccount.getTotalCollectionTransaction()));
			financialAuditDTO.setTotalRefundTransaction(String.valueOf(pullAccount.getTotalRefundTransaction()));
			financialAuditDTO.setTotalCancelledTransaction(String.valueOf(pullAccount.getTotalCancelledTransaction()));
			financialAuditDTO.setTotalSettlementTransaction(String.valueOf(pullAccount.getTotalSettlementTransaction()));
			
			financialAuditDTO.setSystemTotalCollectionAmount(String.format("%.2f", pullAccount.getSystemTotalCollectionAmount()));
			financialAuditDTO.setSystemTotalSettlementAmount(String.format("%.2f", pullAccount.getSystemTotalSettlemetAmount()));
			financialAuditDTO.setSystemTotalCancelledAmount(String.format("%.2f", pullAccount.getSystemTotalCancelledAmount()));
			financialAuditDTO.setSystemTotalAmountRefund(String.format("%.2f", pullAccount.getSystemAmountRefund()));
			
			financialAuditDTO.setSystemTotalAmountCash(String.format("%.2f", pullAccount.getSystemAmountCash()));
			financialAuditDTO.setSystemTotalAmountVisa(String.format("%.2f", pullAccount.getSystemAmountVisa()));
			
			financialAuditDTO.setTotalAmount(String.format("%.2f", pullAccount.getDepositTotalAmount()));
			financialAuditDTO.setTotalAmountCash(String.format("%.2f", pullAccount.getDepositCash()));
			financialAuditDTO.setTotalAmountVisa(String.format("%.2f", pullAccount.getDepositVisa()));
			
			financialAuditDTO.setTotalAmountDeficit(String.format("%.2f", pullAccount.getDeficitAmount()));
			financialAuditDTO.setTotalAmountOver(String.format("%.2f", pullAccount.getOverAmount()));
			if(pullAccount.getDeficitAmount() > 0) {
				financialAuditDTO.setOverOrDeficitAmount("-"+String.format("%.2f", pullAccount.getDeficitAmount()));
			}else if(pullAccount.getOverAmount() > 0){
				financialAuditDTO.setOverOrDeficitAmount("+"+String.format("%.2f", pullAccount.getOverAmount()));
			}
			
			
		}
		
		return financialAuditDTO;
	}

}
