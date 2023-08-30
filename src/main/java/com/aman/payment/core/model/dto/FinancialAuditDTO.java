package com.aman.payment.core.model.dto;

import org.json.JSONException;
import org.json.JSONObject;

public class FinancialAuditDTO {

	private String totalTransaction;
	private String totalCollectionTransaction;
	private String totalRefundTransaction;
	private String totalCancelledTransaction;
	private String totalSettlementTransaction;

	private String sysTotalTransactionsAmount;
	private String systemTotalCollectionAmount;
	private String systemTotalAmountCash;
	private String systemTotalAmountVisa;
	private String systemTotalAmountRefund;
	private String systemTotalCancelledAmount;
	private String systemTotalSettlementAmount;

	private String totalAmount;
	private String totalAmountCash;
	private String totalAmountVisa;
	private String totalAmountDeficit;
	private String totalAmountOver;
	

	private String settlementCode;
	private String posId;
	private String locationName;
	private String locationCode;
	private String agentName;
	private String createDate;
	private String approvedBy;
	private String approvedDate;
	private String serviceId;
	private String courtName;
	private String sectorName;
	
	private String total;
	private String overOrDeficitAmount;
	
	private String comment;
	
	private String count;
	private String amount;
	private String groups;

	public FinancialAuditDTO() {
		super();
	}

	public FinancialAuditDTO(String totalTransaction, String totalCollectionTransaction,
			String totalRefundTransaction, String totalCancelledTransaction, String totalSettlementTransaction,
			String totalAmount, String totalAmountCash, String totalAmountVisa, String systemTotalCollectionAmount,
			String systemTotalAmountCash, String systemTotalAmountVisa, String systemTotalAmountRefund,
			String totalAmountDeficit, String totalAmountOver, String settlementCode, String posId,
			String locationName, String locationCode, String agentName, String createDate, String approvedBy,
			String approvedDate, String systemTotalCancelledAmount, String systemTotalSettlementAmount, 
			String serviceId, String total, String overOrDeficitAmount, String comment,
			String count, String amount, String groups) {
		super();
		this.totalTransaction = totalTransaction;
		this.totalCollectionTransaction = totalCollectionTransaction;
		this.totalRefundTransaction = totalRefundTransaction;
		this.totalCancelledTransaction = totalCancelledTransaction;
		this.totalSettlementTransaction = totalSettlementTransaction;
		this.totalAmount = totalAmount;
		this.totalAmountCash = totalAmountCash;
		this.totalAmountVisa = totalAmountVisa;
		this.systemTotalCollectionAmount = systemTotalCollectionAmount;
		this.systemTotalAmountCash = systemTotalAmountCash;
		this.systemTotalAmountVisa = systemTotalAmountVisa;
		this.systemTotalAmountRefund = systemTotalAmountRefund;
		this.totalAmountDeficit = totalAmountDeficit;
		this.totalAmountOver = totalAmountOver;
		this.settlementCode = settlementCode;
		this.posId = posId;
		this.locationName = locationName;
		this.locationCode = locationCode;
		this.agentName = agentName;
		this.createDate = createDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.systemTotalCancelledAmount = systemTotalCancelledAmount;
		this.systemTotalSettlementAmount = systemTotalSettlementAmount;
		this.serviceId = serviceId;
		this.total = total;
		this.overOrDeficitAmount = overOrDeficitAmount;
		this.comment = comment;
		this.count = count;
		this.amount = amount;
		this.groups = groups;
	}

	public String getTotalTransaction() {
		return totalTransaction;
	}

	public String getTotalCollectionTransaction() {
		return totalCollectionTransaction;
	}

	public String getTotalRefundTransaction() {
		return totalRefundTransaction;
	}

	public String getTotalCancelledTransaction() {
		return totalCancelledTransaction;
	}

	public String getTotalSettlementTransaction() {
		return totalSettlementTransaction;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public String getTotalAmountCash() {
		return totalAmountCash;
	}

	public String getTotalAmountVisa() {
		return totalAmountVisa;
	}

	public String getSystemTotalAmountCash() {
		return systemTotalAmountCash;
	}

	public String getSystemTotalAmountVisa() {
		return systemTotalAmountVisa;
	}

	public String getTotalAmountDeficit() {
		return totalAmountDeficit;
	}

	public String getTotalAmountOver() {
		return totalAmountOver;
	}

	public String getSettlementCode() {
		return settlementCode;
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

	public String getAgentName() {
		return agentName;
	}

	public String getCreateDate() {
		return createDate;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public String getApprovedDate() {
		return approvedDate;
	}

	public void setTotalTransaction(String totalTransaction) {
		this.totalTransaction = totalTransaction;
	}

	public void setTotalCollectionTransaction(String totalCollectionTransaction) {
		this.totalCollectionTransaction = totalCollectionTransaction;
	}

	public void setTotalRefundTransaction(String totalRefundTransaction) {
		this.totalRefundTransaction = totalRefundTransaction;
	}

	public void setTotalCancelledTransaction(String totalCancelledTransaction) {
		this.totalCancelledTransaction = totalCancelledTransaction;
	}

	public void setTotalSettlementTransaction(String totalSettlementTransaction) {
		this.totalSettlementTransaction = totalSettlementTransaction;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public void setTotalAmountCash(String totalAmountCash) {
		this.totalAmountCash = totalAmountCash;
	}

	public void setTotalAmountVisa(String totalAmountVisa) {
		this.totalAmountVisa = totalAmountVisa;
	}

	public void setSystemTotalAmountCash(String systemTotalAmountCash) {
		this.systemTotalAmountCash = systemTotalAmountCash;
	}

	public void setSystemTotalAmountVisa(String systemTotalAmountVisa) {
		this.systemTotalAmountVisa = systemTotalAmountVisa;
	}

	public void setTotalAmountDeficit(String totalAmountDeficit) {
		this.totalAmountDeficit = totalAmountDeficit;
	}

	public void setTotalAmountOver(String totalAmountOver) {
		this.totalAmountOver = totalAmountOver;
	}

	public void setSettlementCode(String settlementCode) {
		this.settlementCode = settlementCode;
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

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public void setApprovedDate(String approvedDate) {
		this.approvedDate = approvedDate;
	}

	public String getSystemTotalAmountRefund() {
		return systemTotalAmountRefund;
	}

	public void setSystemTotalAmountRefund(String systemTotalAmountRefund) {
		this.systemTotalAmountRefund = systemTotalAmountRefund;
	}

	public String getSystemTotalCollectionAmount() {
		return systemTotalCollectionAmount;
	}

	public String getSystemTotalCancelledAmount() {
		return systemTotalCancelledAmount;
	}

	public String getSystemTotalSettlementAmount() {
		return systemTotalSettlementAmount;
	}

	public void setSystemTotalCollectionAmount(String systemTotalCollectionAmount) {
		this.systemTotalCollectionAmount = systemTotalCollectionAmount;
	}

	public void setSystemTotalCancelledAmount(String systemTotalCancelledAmount) {
		this.systemTotalCancelledAmount = systemTotalCancelledAmount;
	}

	public void setSystemTotalSettlementAmount(String systemTotalSettlementAmount) {
		this.systemTotalSettlementAmount = systemTotalSettlementAmount;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getOverOrDeficitAmount() {
		return overOrDeficitAmount;
	}

	public void setOverOrDeficitAmount(String overOrDeficitAmount) {
		this.overOrDeficitAmount = overOrDeficitAmount;
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

	public String getCount() {
		return count;
	}

	public String getAmount() {
		return amount;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getGroups() {
		return groups;
	}

	public void setGroups(String groups) {
		this.groups = groups;
	}


	public String getCourtName() {
		return courtName;
	}

	public void setCourtName(String courtName) {
		this.courtName = courtName;
	}

	public String getSectorName() {
		return sectorName;
	}

	public void setSectorName(String sectorName) {
		this.sectorName = sectorName;
	}

	@Override
	public String toString() {
		
		JSONObject financialAuditDTO = new JSONObject();
		
		try {
			financialAuditDTO.put("totalTransaction", totalTransaction);
			financialAuditDTO.put("totalCollectionTransaction", totalCollectionTransaction);
			financialAuditDTO.put("totalRefundTransaction", totalRefundTransaction);
			financialAuditDTO.put("totalCancelledTransaction", totalCancelledTransaction);
			financialAuditDTO.put("totalSettlementTransaction", totalSettlementTransaction);
			financialAuditDTO.put("totalAmount", totalAmount);
			financialAuditDTO.put("totalAmountCash", totalAmountCash);
			financialAuditDTO.put("totalAmountVisa", totalAmountVisa);
			financialAuditDTO.put("systemTotalCollectionAmount", systemTotalCollectionAmount);
			financialAuditDTO.put("systemTotalSettlementAmount", systemTotalSettlementAmount);
			financialAuditDTO.put("systemTotalCancelledAmount", systemTotalCancelledAmount);
			financialAuditDTO.put("systemTotalAmountCash", systemTotalAmountCash);
			financialAuditDTO.put("systemTotalAmountVisa", systemTotalAmountVisa);
			financialAuditDTO.put("systemTotalAmountRefund", systemTotalAmountRefund);
			financialAuditDTO.put("totalAmountDeficit", totalAmountDeficit);
			financialAuditDTO.put("totalAmountOver", totalAmountOver);
			financialAuditDTO.put("settlementCode", settlementCode);
			financialAuditDTO.put("posId", posId);
			financialAuditDTO.put("locationName", locationName);
			financialAuditDTO.put("locationCode", locationCode);
			financialAuditDTO.put("agentName", agentName);
			financialAuditDTO.put("createDate", createDate);
			financialAuditDTO.put("approvedBy", approvedBy);
			financialAuditDTO.put("approvedDate", approvedDate);
			financialAuditDTO.put("serviceId", serviceId);
			financialAuditDTO.put("total", total);
			financialAuditDTO.put("overOrDeficitAmount", overOrDeficitAmount);
			financialAuditDTO.put("comment", comment);
			financialAuditDTO.put("sysTotalTransactionsAmount", sysTotalTransactionsAmount);
			financialAuditDTO.put("count", count);
			financialAuditDTO.put("amount", amount);
			financialAuditDTO.put("groups", groups);
			financialAuditDTO.put("courtName", courtName);
			financialAuditDTO.put("sectorName", sectorName);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return financialAuditDTO.toString();
		
	}

}