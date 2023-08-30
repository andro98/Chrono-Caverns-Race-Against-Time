package com.aman.payment.maazoun.model.dto;

import org.json.JSONException;
import org.json.JSONObject;

public class MaazounContractAuditDTO {

	private String totalTransaction;
	
	private String totalCollectionTransaction;
	private String totalSettlementTransaction;
	private String totalRefundTransaction;
	private String totalCancelledTransaction;
	
	private String totalHoldTransaction;
	private String totalFinalTransaction;
	private String totalSynchedTransaction;
	private String totalMatchedTransaction;
	private String totalModifiedTransaction;


	private String systemTotalCollectionAmount;
	private String systemTotalSettlementAmount;
	private String systemTotalAmountRefund;
	private String systemTotalCancelledAmount;
	private String systemTotalAmountCash;
	private String systemTotalAmountVisa;
	

	private String settlementCode;
	private String posId;
	private String locationName;
	private String locationCode;
	private String locationId;
	private String agentName;
	private String createDate;
	private String courtName;
	private String sectorName;
	
	private String count;
	private String amount;
	private String groups;
	private String subServiceName;

	public MaazounContractAuditDTO() {
		super();
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


	public String getSystemTotalCollectionAmount() {
		return systemTotalCollectionAmount;
	}


	public String getSystemTotalAmountCash() {
		return systemTotalAmountCash;
	}


	public String getSystemTotalAmountVisa() {
		return systemTotalAmountVisa;
	}


	public String getSystemTotalAmountRefund() {
		return systemTotalAmountRefund;
	}


	public String getSystemTotalCancelledAmount() {
		return systemTotalCancelledAmount;
	}


	public String getSystemTotalSettlementAmount() {
		return systemTotalSettlementAmount;
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


	public void setSystemTotalCollectionAmount(String systemTotalCollectionAmount) {
		this.systemTotalCollectionAmount = systemTotalCollectionAmount;
	}


	public void setSystemTotalAmountCash(String systemTotalAmountCash) {
		this.systemTotalAmountCash = systemTotalAmountCash;
	}


	public void setSystemTotalAmountVisa(String systemTotalAmountVisa) {
		this.systemTotalAmountVisa = systemTotalAmountVisa;
	}


	public void setSystemTotalAmountRefund(String systemTotalAmountRefund) {
		this.systemTotalAmountRefund = systemTotalAmountRefund;
	}


	public void setSystemTotalCancelledAmount(String systemTotalCancelledAmount) {
		this.systemTotalCancelledAmount = systemTotalCancelledAmount;
	}


	public void setSystemTotalSettlementAmount(String systemTotalSettlementAmount) {
		this.systemTotalSettlementAmount = systemTotalSettlementAmount;
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

	public String getTotalHoldTransaction() {
		return totalHoldTransaction;
	}


	public String getTotalFinalTransaction() {
		return totalFinalTransaction;
	}


	public String getTotalSynchedTransaction() {
		return totalSynchedTransaction;
	}


	public String getTotalMatchedTransaction() {
		return totalMatchedTransaction;
	}


	public String getTotalModifiedTransaction() {
		return totalModifiedTransaction;
	}


	public void setTotalHoldTransaction(String totalHoldTransaction) {
		this.totalHoldTransaction = totalHoldTransaction;
	}


	public void setTotalFinalTransaction(String totalFinalTransaction) {
		this.totalFinalTransaction = totalFinalTransaction;
	}


	public void setTotalSynchedTransaction(String totalSynchedTransaction) {
		this.totalSynchedTransaction = totalSynchedTransaction;
	}


	public void setTotalMatchedTransaction(String totalMatchedTransaction) {
		this.totalMatchedTransaction = totalMatchedTransaction;
	}


	public void setTotalModifiedTransaction(String totalModifiedTransaction) {
		this.totalModifiedTransaction = totalModifiedTransaction;
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


	public String getLocationId() {
		return locationId;
	}


	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}


	public String getGroups() {
		return groups;
	}


	public void setGroups(String groups) {
		this.groups = groups;
	}


	public String getSubServiceName() {
		return subServiceName;
	}


	public void setSubServiceName(String subServiceName) {
		this.subServiceName = subServiceName;
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
		
		return new JSONObject(this).toString();
		
	}


}