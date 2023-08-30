package com.aman.payment.auth.model.dto;

import org.json.JSONObject;

public class SubServiceQuotaDTO{

    private String id;
    private String name;
    private String fees;
    private String status;
    private String subServiceId;
    private String subServiceName;
    private String feesType;
    private String midAccount;
    private String midBank;
    private String midBenficiary;
    private String description;

    public SubServiceQuotaDTO() {
		super();
	}

	public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public String getFees() {
        return this.fees;
    }

    public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSubServiceId() {
		return subServiceId;
	}

	public void setSubServiceId(String subServiceId) {
		this.subServiceId = subServiceId;
	}

	public String getSubServiceName() {
		return subServiceName;
	}

	public void setSubServiceName(String subServiceName) {
		this.subServiceName = subServiceName;
	}

	public String getFeesType() {
		return feesType;
	}

	public void setFeesType(String feesType) {
		this.feesType = feesType;
	}

	public String getMidAccount() {
		return midAccount;
	}

	public void setMidAccount(String midAccount) {
		this.midAccount = midAccount;
	}

	public String getMidBank() {
		return midBank;
	}

	public void setMidBank(String midBank) {
		this.midBank = midBank;
	}

	public String getMidBenficiary() {
		return midBenficiary;
	}

	public void setMidBenficiary(String midBenficiary) {
		this.midBenficiary = midBenficiary;
	}
	 
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {

		return new JSONObject(this).toString();

	}

}