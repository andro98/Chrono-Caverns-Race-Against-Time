package com.aman.payment.auth.model.payload;

import javax.validation.constraints.NotBlank;

import com.aman.payment.auth.service.CryptoMngrAuthService;

import io.swagger.annotations.ApiModelProperty;

public class UpdateCurrentUserPasswordRequest implements AuthBasePayload<UpdateCurrentUserPasswordRequest>{

	private String userId;
	private String userName;
    private String oldPassword;


    @NotBlank(message = "New password must not be blank")
    @ApiModelProperty(value = "Valid new password string", required = true, allowableValues = "NonEmpty String")
    private String newPassword;


    public UpdateCurrentUserPasswordRequest(String newPassword, String userId, String userName, String oldPassword) {
        this.userId = userId;
        this.newPassword = newPassword;
        this.userName = userName;
        this.oldPassword = oldPassword;
    }

    public UpdateCurrentUserPasswordRequest() {
    }

	public String getUserId() {
		return userId;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
	

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	@Override
	public UpdateCurrentUserPasswordRequest decrypt(CryptoMngrAuthService cryptoMngrAuthService) {
		return new UpdateCurrentUserPasswordRequest(cryptoMngrAuthService.decrypt(newPassword), 
				cryptoMngrAuthService.decrypt(userId),
				cryptoMngrAuthService.decrypt(userName),
				cryptoMngrAuthService.decrypt(oldPassword)
				);
	}
	}



