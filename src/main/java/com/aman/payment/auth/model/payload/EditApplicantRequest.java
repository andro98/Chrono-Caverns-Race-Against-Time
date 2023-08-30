package com.aman.payment.auth.model.payload;

import com.aman.payment.auth.service.CryptoMngrAuthService;

public class EditApplicantRequest implements AuthBasePayload<EditApplicantRequest> {

	private String id;
	private String mobile;
    private String nationalId;


	public EditApplicantRequest() {
		super();
	}

	public EditApplicantRequest(String id, String mobile, String nationalId) {
		super();
		this.id = id;
		this.mobile = mobile;
		this.nationalId = nationalId;

	}

	public String getId() {
		return id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	

	public String getNationalId() {
		return nationalId;
	}

	public void setNationalId(String nationalId) {
		this.nationalId = nationalId;
	}

	@Override
	public EditApplicantRequest decrypt(CryptoMngrAuthService cryptoMngrAuthService) {
		return new EditApplicantRequest(cryptoMngrAuthService.decrypt(id), 
				cryptoMngrAuthService.decrypt(mobile),
				cryptoMngrAuthService.decrypt(nationalId));
	}
	
}
