package com.aman.payment.core.model.dto;

import org.json.JSONException;
import org.json.JSONObject;

public class PullAccountDTO {

	private String id;

	private String sysTotalTransactions;
	private String sysTotalCollectionTransactions;
	private String sysTotalRefundTransactions;
	private String sysTotalCancelledTransactions;
	private String sysTotalSettlementTransaction;

	private String sysSettlementAmountVisa;
	private String sysSettlementAmountCash;
	private String sysAmountVisa;
	private String sysAmountCash;
	private String sysAmountRefund;
	private String sysTotalTransactionsAmount;
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
	
	private String settlementCode;
	private String createdAt;
	private String status;
	private String location;
	private String comment;
	private String approvedBy;
	private String approvedComment;
	private String approvedAt;
	private String createdBy;
	
	private String serviceId;

	public PullAccountDTO(String id, String sysTotalTransactions, String sysTotalCollectionTransactions,
			String sysTotalRefundTransactions, String sysTotalCancelledTransactions,
			String sysTotalSettlementTransaction, String sysAmountVisa, String sysAmountCash, String sysAmountRefund,
			String sysTotalTransactionsAmount, String sysTotalCollectionAmount, String sysTotalCancelledAmount,
			String sysTotalSettlemetAmount, String settlementCode, String totalDeposit,
			String totalDepositCash, String totalDepositVisa, String createdAt, String status, String location,
			String approvedBy, String approvedComment, String approvedAt, String sysSettlementAmountVisa,
			String sysSettlementAmountCash, String serviceId, String comment, String createdBy) {
		super();
		this.id = id;
		this.sysTotalTransactions = sysTotalTransactions;
		this.sysTotalCollectionTransactions = sysTotalCollectionTransactions;
		this.sysTotalRefundTransactions = sysTotalRefundTransactions;
		this.sysTotalCancelledTransactions = sysTotalCancelledTransactions;
		this.sysTotalSettlementTransaction = sysTotalSettlementTransaction;
		this.sysAmountVisa = sysAmountVisa;
		this.sysAmountCash = sysAmountCash;
		this.sysAmountRefund = sysAmountRefund;
		this.sysTotalTransactionsAmount = sysTotalTransactionsAmount;
		this.sysTotalCollectionAmount = sysTotalCollectionAmount;
		this.sysTotalCancelledAmount = sysTotalCancelledAmount;
		this.sysTotalSettlemetAmount = sysTotalSettlemetAmount;
		this.settlementCode = settlementCode;
		this.totalDeposit = totalDeposit;
		this.totalDepositCash = totalDepositCash;
		this.totalDepositVisa = totalDepositVisa;
		this.createdAt = createdAt;
		this.status = status;
		this.location = location;
		this.approvedBy = approvedBy;
		this.approvedComment = approvedComment;
		this.approvedAt = approvedAt;
		this.sysSettlementAmountVisa = sysSettlementAmountVisa;
		this.sysSettlementAmountCash = sysSettlementAmountCash;
		this.serviceId = serviceId;
		this.comment = comment;
		this.createdBy = createdBy ;
	}

	public PullAccountDTO() {
		super();
	}

	public String getId() {
		return id;
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

	public String getSettlementCode() {
		return settlementCode;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public String getStatus() {
		return status;
	}

	public String getLocation() {
		return location;
	}

	public void setId(String id) {
		this.id = id;
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

	public void setSettlementCode(String settlementCode) {
		this.settlementCode = settlementCode;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setLocation(String location) {
		this.location = location;
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

	public void setTotalDeposit(String totalDeposit) {
		this.totalDeposit = totalDeposit;
	}

	public void setTotalDepositCash(String totalDepositCash) {
		this.totalDepositCash = totalDepositCash;
	}

	public void setTotalDepositVisa(String totalDepositVisa) {
		this.totalDepositVisa = totalDepositVisa;
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

	public String getSysSettlementAmountVisa() {
		return sysSettlementAmountVisa;
	}

	public String getSysSettlementAmountCash() {
		return sysSettlementAmountCash;
	}

	public void setSysSettlementAmountVisa(String sysSettlementAmountVisa) {
		this.sysSettlementAmountVisa = sysSettlementAmountVisa;
	}

	public void setSysSettlementAmountCash(String sysSettlementAmountCash) {
		this.sysSettlementAmountCash = sysSettlementAmountCash;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getSysTotalTransactionsAmount() {
		return sysTotalTransactionsAmount;
	}

	public void setSysTotalTransactionsAmount(String sysTotalTransactionsAmount) {
		this.sysTotalTransactionsAmount = sysTotalTransactionsAmount;
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
	
	

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Override
	public String toString() {
		
		JSONObject pullAccountDTO = new JSONObject();
		
		try {
			pullAccountDTO.put("id", id);
			pullAccountDTO.put("sysTotalTransactions", sysTotalTransactions);
			pullAccountDTO.put("sysTotalCollectionTransactions", sysTotalCollectionTransactions);
			pullAccountDTO.put("sysTotalRefundTransactions", sysTotalRefundTransactions);
			pullAccountDTO.put("sysTotalCancelledTransactions", sysTotalCancelledTransactions);
			pullAccountDTO.put("sysTotalSettlementTransaction", sysTotalSettlementTransaction);
			pullAccountDTO.put("sysSettlementAmountCash", sysSettlementAmountCash);
			pullAccountDTO.put("sysSettlementAmountVisa", sysSettlementAmountVisa);
			pullAccountDTO.put("sysAmountVisa", sysAmountVisa);
			pullAccountDTO.put("sysAmountCash", sysAmountCash);
			pullAccountDTO.put("sysAmountRefund", sysAmountRefund);
			pullAccountDTO.put("sysTotalTransactionsAmount", sysTotalTransactionsAmount);
			pullAccountDTO.put("sysTotalCollectionAmount", sysTotalCollectionAmount);
			pullAccountDTO.put("sysTotalCancelledAmount", sysTotalCancelledAmount);
			pullAccountDTO.put("sysTotalSettlemetAmount", sysTotalSettlemetAmount);
			pullAccountDTO.put("totalDeposit", totalDeposit);
			pullAccountDTO.put("totalDepositCash", totalDepositCash);
			pullAccountDTO.put("totalDepositVisa", totalDepositVisa);
			pullAccountDTO.put("settlementCode", settlementCode);
			pullAccountDTO.put("createdAt", createdAt);
			pullAccountDTO.put("status", status);
			pullAccountDTO.put("location", location);
			pullAccountDTO.put("approvedBy", approvedBy);
			pullAccountDTO.put("approvedComment", approvedComment);
			pullAccountDTO.put("approvedAt", approvedAt);
			pullAccountDTO.put("serviceId", serviceId);
			pullAccountDTO.put("sysTotalDepositTransactions",sysTotalDepositTransactions);
			pullAccountDTO.put("sysTotalDepositAmount",sysTotalDepositAmount);
			pullAccountDTO.put("sysTotalDepositAmountCash",sysTotalDepositAmountCash);
			pullAccountDTO.put("sysTotalDepositAmountVisa",sysTotalDepositAmountVisa);
			pullAccountDTO.put("comment",comment);
			pullAccountDTO.put("createdBy",createdBy);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return pullAccountDTO.toString();
		
	}

	

}