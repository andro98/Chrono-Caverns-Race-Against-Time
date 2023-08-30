package com.aman.payment.auth.model.payload;

import com.aman.payment.auth.service.CryptoMngrAuthService;

public class AddEditSubServiceQuotaRequest implements AuthBasePayload<AddEditSubServiceQuotaRequest>  {

	private String id;
	private String name;
	private String fees;
	private String description;
	private String statusFk;
	private String feesType;
	private String midAccount;
	private String midBank;
	private String midBenficiary;
	private String subServiceFk;
	
	

	public AddEditSubServiceQuotaRequest() {
		// TODO Auto-generated constructor stub
	}
	
	

	public AddEditSubServiceQuotaRequest(String id, String name, String fees, String description, String statusFk,
			String feesType, String midAccount, String midBank, String midBenficiary, String subServiceFk) {
		super();
		this.id = id;
		this.name = name;
		this.fees = fees;
		this.description = description;
		this.statusFk = statusFk;
		this.feesType = feesType;
		this.midAccount = midAccount;
		this.midBank = midBank;
		this.midBenficiary = midBenficiary;
		this.subServiceFk = subServiceFk;
	}



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

 

	public String getFees() {
		return fees;
	}
    public void setFees(String fees) {
		this.fees = fees;
	}
 
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatusFk() {
		return statusFk;
	}

	public void setStatusFk(String statusFk) {
		this.statusFk = statusFk;
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



	public String getSubServiceFk() {
		return subServiceFk;
	}

	public void setSubServiceFk(String subServiceFk) {
		this.subServiceFk = subServiceFk;
	}

	@Override
	public AddEditSubServiceQuotaRequest decrypt(CryptoMngrAuthService cryptoMngrAuthService) {
		// TODO Auto-generated method stub
		return  new AddEditSubServiceQuotaRequest(
				cryptoMngrAuthService.decrypt(id), 
				cryptoMngrAuthService.decrypt(name), 
				cryptoMngrAuthService.decrypt(fees), 
				cryptoMngrAuthService.decrypt(description), 
				cryptoMngrAuthService.decrypt(statusFk),
				cryptoMngrAuthService.decrypt(feesType),
				cryptoMngrAuthService.decrypt(midAccount),
				cryptoMngrAuthService.decrypt(midBank),
				cryptoMngrAuthService.decrypt(midBenficiary),
				cryptoMngrAuthService.decrypt(subServiceFk)) ;
	}
	
	 

}
