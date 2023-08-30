package com.aman.payment.auth.model.payload;

import com.aman.payment.auth.service.CryptoMngrAuthService;

import io.swagger.annotations.ApiModelProperty;

public class PosUsersReuest implements AuthBasePayload<PosUsersReuest> {
	
	
	@ApiModelProperty(value = "Pos ID", allowableValues = "NonEmpty String", allowEmptyValue = false)
    private String posId;
	
	public PosUsersReuest() {
		super();
	}

	public PosUsersReuest(String posId) {
		super();
		this.posId = posId;
	}

	 

	public String getPosId() {
		return posId;
	}

	public void setPosId(String posId) {
		this.posId = posId;
	}

	@Override
	public PosUsersReuest decrypt(CryptoMngrAuthService cryptoMngrAuthService) {
		return new PosUsersReuest(cryptoMngrAuthService.decrypt(posId));
	}
	
}
