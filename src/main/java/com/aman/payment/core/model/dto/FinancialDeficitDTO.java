package com.aman.payment.core.model.dto;

import org.json.JSONException;
import org.json.JSONObject;

public class FinancialDeficitDTO {

	private String financialDeficitId;
	private String deficitAmount;
	private String username;
	private String settlementCode;
	private String createdAt;
	private String pullAccountId;
	private String posId;
	private String locationName;
	private String locationCode;
	private String attUrl;

	private String approvedBy;
	private String approvedComment;
	private String approvedAt;
	private String status;
	private String comment;
	private String pullAccountStatus;
	private String serviceId;

	public FinancialDeficitDTO() {
	}

	public FinancialDeficitDTO(String financialDeficitId, String deficitAmount, String username, String settlementCode,
			String createdAt, String pullAccountId, String posId, String locationName, String locationCode,
			String attUrl, String approvedBy, String approvedComment, String approvedAt, String status, String comment,
			String pullAccountStatus, String serviceId) {
		super();
		this.financialDeficitId = financialDeficitId;
		this.deficitAmount = deficitAmount;
		this.username = username;
		this.settlementCode = settlementCode;
		this.createdAt = createdAt;
		this.pullAccountId = pullAccountId;
		this.posId = posId;
		this.locationName = locationName;
		this.locationCode = locationCode;
		this.attUrl = attUrl;
		this.approvedBy = approvedBy;
		this.approvedComment = approvedComment;
		this.approvedAt = approvedAt;
		this.status = status;
		this.comment = comment;
		this.pullAccountStatus = pullAccountStatus;
		this.serviceId = serviceId;
	}

	public String getFinancialDeficitId() {
		return financialDeficitId;
	}

	public String getDeficitAmount() {
		return deficitAmount;
	}

	public String getUsername() {
		return username;
	}

	public String getSettlementCode() {
		return settlementCode;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public String getPullAccountId() {
		return pullAccountId;
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

	public String getAttUrl() {
		return attUrl;
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

	public String getStatus() {
		return status;
	}

	public String getComment() {
		return comment;
	}

	public String getPullAccountStatus() {
		return pullAccountStatus;
	}

	public void setFinancialDeficitId(String financialDeficitId) {
		this.financialDeficitId = financialDeficitId;
	}

	public void setDeficitAmount(String deficitAmount) {
		this.deficitAmount = deficitAmount;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setSettlementCode(String settlementCode) {
		this.settlementCode = settlementCode;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public void setPullAccountId(String pullAccountId) {
		this.pullAccountId = pullAccountId;
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

	public void setAttUrl(String attUrl) {
		this.attUrl = attUrl;
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

	public void setStatus(String status) {
		this.status = status;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setPullAccountStatus(String pullAccountStatus) {
		this.pullAccountStatus = pullAccountStatus;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	@Override
	public String toString() {
		
		JSONObject financialDeficitDTO = new JSONObject();
		
		try {
			financialDeficitDTO.put("financialDeficitId", financialDeficitId);
			financialDeficitDTO.put("deficitAmount", deficitAmount);
			financialDeficitDTO.put("username", username);
			financialDeficitDTO.put("settlementCode", settlementCode);
			financialDeficitDTO.put("createdAt", createdAt);
			financialDeficitDTO.put("pullAccountId", pullAccountId);
			financialDeficitDTO.put("posId", posId);
			financialDeficitDTO.put("locationName", locationName);
			financialDeficitDTO.put("locationCode", locationCode);
			financialDeficitDTO.put("attUrl", attUrl);
			financialDeficitDTO.put("approvedBy", approvedBy);
			financialDeficitDTO.put("approvedComment", approvedComment);
			financialDeficitDTO.put("approvedAt", approvedAt);
			financialDeficitDTO.put("status", status);
			financialDeficitDTO.put("comment", comment);
			financialDeficitDTO.put("pullAccountStatus", pullAccountStatus);
			financialDeficitDTO.put("serviceId", serviceId);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return financialDeficitDTO.toString();
		
	}

	

}