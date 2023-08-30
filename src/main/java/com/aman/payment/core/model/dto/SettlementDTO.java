package com.aman.payment.core.model.dto;

import org.json.JSONException;
import org.json.JSONObject;

public class SettlementDTO {

	private String settlementId;
	private String settlementCode;

	private String sysTotalTransactions;
	private String sysTotalCollectionTransactions;
	private String sysTotalRefundTransactions;
	private String sysTotalCancelledTransactions;
	private String sysTotalSettlementTransaction;

	private String sysTotalTransactionsAmount;
	private String sysAmountVisa;
	private String sysAmountCash;
	private String sysAmountRefund;
	private String sysTotalCollectionAmount;
	private String sysTotalCancelledAmount;
	private String sysTotalSettlemetAmount;
	
	private String sysTotalDepositTransactions;
	private String sysTotalDepositAmount;
	private String sysTotalDepositAmountCash;
	private String sysTotalDepositAmountVisa;

	private String totalDeposit;
	private String totalDepositCash;
	private String totalDepositVisa;

	private String agentName;
	private String posId;
	private String locationName;
	private String locationCode;
	private String transactionDatetime;

	private String attBankUrl;
	private String attVisaUrl;
	private String status;

	private String isDeficit;
	private String deficitAmount;
	private String isOver;
	private String overAmount;
	private String settlementApprovedStatus;
	private String comment;

	private String overDeficitAmount;
	private String deficitStatus;
	private String totalSettlementTransaction;

	private String approvedBy;
	private String approvedComment;
	private String approvedAt;
	
	private String serviceId;

	public SettlementDTO() {
	}

	public SettlementDTO(String settlementId, String settlementCode, String sysTotalTransactions,
			String sysTotalCollectionTransactions, String sysTotalRefundTransactions,
			String sysTotalCancelledTransactions, String sysTotalSettlementTransaction, String sysAmountVisa,
			String sysAmountCash, String sysAmountRefund, String sysTotalCollectionAmount, 
			String sysTotalCancelledAmount, String sysTotalSettlemetAmount,
			String totalDeposit, String totalDepositCash, String totalDepositVisa, String agentName, String posId,
			String locationName, String locationCode, String transactionDatetime, String attBankUrl, String attVisaUrl,
			String status, String isDeficit, String deficitAmount, String isOver, String overAmount,
			String settlementApprovedStatus, String comment, String overDeficitAmount, String deficitStatus,
			String totalSettlementTransaction, String approvedBy,
			String approvedComment, String approvedAt, String serviceId) {
		super();
		this.settlementId = settlementId;
		this.settlementCode = settlementCode;
		this.sysTotalTransactions = sysTotalTransactions;
		this.sysTotalCollectionTransactions = sysTotalCollectionTransactions;
		this.sysTotalRefundTransactions = sysTotalRefundTransactions;
		this.sysTotalCancelledTransactions = sysTotalCancelledTransactions;
		this.sysTotalSettlementTransaction = sysTotalSettlementTransaction;
		this.sysAmountVisa = sysAmountVisa;
		this.sysAmountCash = sysAmountCash;
		this.sysAmountRefund = sysAmountRefund;
		this.sysTotalCollectionAmount = sysTotalCollectionAmount;
		this.sysTotalCancelledAmount = sysTotalCancelledAmount;
		this.sysTotalSettlemetAmount = sysTotalSettlemetAmount;
		this.totalDeposit = totalDeposit;
		this.totalDepositCash = totalDepositCash;
		this.totalDepositVisa = totalDepositVisa;
		this.agentName = agentName;
		this.posId = posId;
		this.locationName = locationName;
		this.locationCode = locationCode;
		this.transactionDatetime = transactionDatetime;
		this.attBankUrl = attBankUrl;
		this.attVisaUrl = attVisaUrl;
		this.status = status;
		this.isDeficit = isDeficit;
		this.deficitAmount = deficitAmount;
		this.isOver = isOver;
		this.overAmount = overAmount;
		this.settlementApprovedStatus = settlementApprovedStatus;
		this.comment = comment;
		this.overDeficitAmount = overDeficitAmount;
		this.deficitStatus = deficitStatus;
		this.totalSettlementTransaction = totalSettlementTransaction;
		this.approvedBy = approvedBy;
		this.approvedComment = approvedComment;
		this.approvedAt = approvedAt;
		this.serviceId = serviceId;
	}

	public String getSettlementId() {
		return settlementId;
	}

	public String getSettlementCode() {
		return settlementCode;
	}

	public String getAgentName() {
		return agentName;
	}

	public String getPosId() {
		return posId;
	}

	public String getLocationName() {
		return locationName;
	}

	public String getLocationCode() {
		return locationCode;
	}

	public String getTransactionDatetime() {
		return transactionDatetime;
	}

	public String getAttBankUrl() {
		return attBankUrl;
	}

	public String getAttVisaUrl() {
		return attVisaUrl;
	}

	public String getStatus() {
		return status;
	}

	public String getIsDeficit() {
		return isDeficit;
	}

	public String getDeficitAmount() {
		return deficitAmount;
	}

	public String getIsOver() {
		return isOver;
	}

	public String getOverAmount() {
		return overAmount;
	}

	public String getSettlementApprovedStatus() {
		return settlementApprovedStatus;
	}

	public String getComment() {
		return comment;
	}

	public String getOverDeficitAmount() {
		return overDeficitAmount;
	}

	public String getDeficitStatus() {
		return deficitStatus;
	}

	public String getTotalSettlementTransaction() {
		return totalSettlementTransaction;
	}

	public void setSettlementId(String settlementId) {
		this.settlementId = settlementId;
	}

	public void setSettlementCode(String settlementCode) {
		this.settlementCode = settlementCode;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public void setPosId(String posId) {
		this.posId = posId;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public void setTransactionDatetime(String transactionDatetime) {
		this.transactionDatetime = transactionDatetime;
	}

	public void setAttBankUrl(String attBankUrl) {
		this.attBankUrl = attBankUrl;
	}

	public void setAttVisaUrl(String attVisaUrl) {
		this.attVisaUrl = attVisaUrl;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setIsDeficit(String isDeficit) {
		this.isDeficit = isDeficit;
	}

	public void setDeficitAmount(String deficitAmount) {
		this.deficitAmount = deficitAmount;
	}

	public void setIsOver(String isOver) {
		this.isOver = isOver;
	}

	public void setOverAmount(String overAmount) {
		this.overAmount = overAmount;
	}

	public void setSettlementApprovedStatus(String settlementApprovedStatus) {
		this.settlementApprovedStatus = settlementApprovedStatus;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setOverDeficitAmount(String overDeficitAmount) {
		this.overDeficitAmount = overDeficitAmount;
	}

	public void setDeficitStatus(String deficitStatus) {
		this.deficitStatus = deficitStatus;
	}

	public void setTotalSettlementTransaction(String totalSettlementTransaction) {
		this.totalSettlementTransaction = totalSettlementTransaction;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public String getApprovedComment() {
		return approvedComment;
	}

	public String getApprovedAt() {
		return approvedAt;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public void setApprovedComment(String approvedComment) {
		this.approvedComment = approvedComment;
	}

	public void setApprovedAt(String approvedAt) {
		this.approvedAt = approvedAt;
	}

	public String getSysTotalTransactions() {
		return sysTotalTransactions;
	}

	public String getSysTotalCollectionTransactions() {
		return sysTotalCollectionTransactions;
	}

	public String getSysTotalRefundTransactions() {
		return sysTotalRefundTransactions;
	}

	public String getSysTotalCancelledTransactions() {
		return sysTotalCancelledTransactions;
	}

	public String getSysTotalSettlementTransaction() {
		return sysTotalSettlementTransaction;
	}

	public String getSysAmountVisa() {
		return sysAmountVisa;
	}

	public String getSysAmountCash() {
		return sysAmountCash;
	}

	public String getSysAmountRefund() {
		return sysAmountRefund;
	}

	public String getSysTotalSettlemetAmount() {
		return sysTotalSettlemetAmount;
	}

	public String getTotalDeposit() {
		return totalDeposit;
	}

	public String getTotalDepositCash() {
		return totalDepositCash;
	}

	public String getTotalDepositVisa() {
		return totalDepositVisa;
	}

	public void setSysTotalTransactions(String sysTotalTransactions) {
		this.sysTotalTransactions = sysTotalTransactions;
	}

	public void setSysTotalCollectionTransactions(String sysTotalCollectionTransactions) {
		this.sysTotalCollectionTransactions = sysTotalCollectionTransactions;
	}

	public void setSysTotalRefundTransactions(String sysTotalRefundTransactions) {
		this.sysTotalRefundTransactions = sysTotalRefundTransactions;
	}

	public void setSysTotalCancelledTransactions(String sysTotalCancelledTransactions) {
		this.sysTotalCancelledTransactions = sysTotalCancelledTransactions;
	}

	public void setSysTotalSettlementTransaction(String sysTotalSettlementTransaction) {
		this.sysTotalSettlementTransaction = sysTotalSettlementTransaction;
	}

	public void setSysAmountVisa(String sysAmountVisa) {
		this.sysAmountVisa = sysAmountVisa;
	}

	public void setSysAmountCash(String sysAmountCash) {
		this.sysAmountCash = sysAmountCash;
	}

	public void setSysAmountRefund(String sysAmountRefund) {
		this.sysAmountRefund = sysAmountRefund;
	}

	public void setSysTotalSettlemetAmount(String sysTotalSettlemetAmount) {
		this.sysTotalSettlemetAmount = sysTotalSettlemetAmount;
	}

	public void setTotalDeposit(String totalDeposit) {
		this.totalDeposit = totalDeposit;
	}

	public void setTotalDepositCash(String totalDepositCash) {
		this.totalDepositCash = totalDepositCash;
	}

	public void setTotalDepositVisa(String totalDepositVisa) {
		this.totalDepositVisa = totalDepositVisa;
	}

	public String getSysTotalCollectionAmount() {
		return sysTotalCollectionAmount;
	}

	public String getSysTotalCancelledAmount() {
		return sysTotalCancelledAmount;
	}

	public void setSysTotalCollectionAmount(String sysTotalCollectionAmount) {
		this.sysTotalCollectionAmount = sysTotalCollectionAmount;
	}

	public void setSysTotalCancelledAmount(String sysTotalCancelledAmount) {
		this.sysTotalCancelledAmount = sysTotalCancelledAmount;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	@Override
	public String toString() {
		
		JSONObject settlementDTO = new JSONObject();
		
		try {
			settlementDTO.put("settlementId", settlementId);
			settlementDTO.put("settlementCode", settlementCode);
			settlementDTO.put("sysTotalTransactions", sysTotalTransactions);
			settlementDTO.put("sysTotalCollectionTransactions", sysTotalCollectionTransactions);
			settlementDTO.put("sysTotalRefundTransactions", sysTotalRefundTransactions);
			settlementDTO.put("sysTotalCancelledTransactions", sysTotalCancelledTransactions);
			settlementDTO.put("sysTotalSettlementTransaction", sysTotalSettlementTransaction);
			settlementDTO.put("sysAmountVisa", sysAmountVisa);
			settlementDTO.put("sysAmountCash", sysAmountCash);
			settlementDTO.put("sysAmountRefund", sysAmountRefund);
			settlementDTO.put("sysTotalCollectionAmount", sysTotalCollectionAmount);
			settlementDTO.put("sysTotalCancelledAmount", sysTotalCancelledAmount);
			settlementDTO.put("sysTotalSettlemetAmount", sysTotalSettlemetAmount);
			settlementDTO.put("totalDeposit", totalDeposit);
			settlementDTO.put("totalDepositCash", totalDepositCash);
			settlementDTO.put("totalDepositVisa", totalDepositVisa);
			settlementDTO.put("settlementCode", settlementCode);
			settlementDTO.put("agentName", agentName);
			settlementDTO.put("status", status);
			settlementDTO.put("posId", posId);
			settlementDTO.put("locationName", locationName);
			settlementDTO.put("locationCode", locationCode);
			settlementDTO.put("transactionDatetime", transactionDatetime);
			settlementDTO.put("attBankUrl", attBankUrl);
			settlementDTO.put("attVisaUrl", attVisaUrl);
			settlementDTO.put("isDeficit", isDeficit);
			settlementDTO.put("deficitAmount", deficitAmount);
			settlementDTO.put("isOver", isOver);
			settlementDTO.put("overAmount", overAmount);
			settlementDTO.put("settlementApprovedStatus", settlementApprovedStatus);
			settlementDTO.put("comment", comment);
			settlementDTO.put("overDeficitAmount", overDeficitAmount);
			settlementDTO.put("deficitStatus", deficitStatus);
			settlementDTO.put("totalSettlementTransaction", totalSettlementTransaction);
			settlementDTO.put("approvedBy", approvedBy);
			settlementDTO.put("approvedComment", approvedComment);
			settlementDTO.put("approvedAt", approvedAt);
			settlementDTO.put("serviceId", serviceId);
			settlementDTO.put("sysTotalDepositTransactions",sysTotalDepositTransactions);
			settlementDTO.put("sysTotalDepositAmount",sysTotalDepositAmount);
			settlementDTO.put("sysTotalDepositAmountCash",sysTotalDepositAmountCash);
			settlementDTO.put("sysTotalDepositAmountVisa",sysTotalDepositAmountVisa);
			settlementDTO.put("sysTotalTransactionsAmount",sysTotalTransactionsAmount);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return settlementDTO.toString();
	}

	public String getSysTotalDepositTransactions() {
		return sysTotalDepositTransactions;
	}

	public String getSysTotalDepositAmount() {
		return sysTotalDepositAmount;
	}

	public String getSysTotalDepositAmountCash() {
		return sysTotalDepositAmountCash;
	}

	public String getSysTotalDepositAmountVisa() {
		return sysTotalDepositAmountVisa;
	}

	public void setSysTotalDepositTransactions(String sysTotalDepositTransactions) {
		this.sysTotalDepositTransactions = sysTotalDepositTransactions;
	}

	public void setSysTotalDepositAmount(String sysTotalDepositAmount) {
		this.sysTotalDepositAmount = sysTotalDepositAmount;
	}

	public void setSysTotalDepositAmountCash(String sysTotalDepositAmountCash) {
		this.sysTotalDepositAmountCash = sysTotalDepositAmountCash;
	}

	public void setSysTotalDepositAmountVisa(String sysTotalDepositAmountVisa) {
		this.sysTotalDepositAmountVisa = sysTotalDepositAmountVisa;
	}

	public String getSysTotalTransactionsAmount() {
		return sysTotalTransactionsAmount;
	}

	public void setSysTotalTransactionsAmount(String sysTotalTransactionsAmount) {
		this.sysTotalTransactionsAmount = sysTotalTransactionsAmount;
	}

	

}