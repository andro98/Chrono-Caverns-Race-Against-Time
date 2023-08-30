/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.aman.payment.auth.model.payload;

import javax.validation.constraints.NotBlank;

import com.aman.payment.auth.service.CryptoMngrAuthService;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Update password Request", description = "The update password request payload")
public class UpdatePasswordRequest implements AuthBasePayload<UpdatePasswordRequest>{

	private String userId;
	private String userName;


    @NotBlank(message = "New password must not be blank")
    @ApiModelProperty(value = "Valid new password string", required = true, allowableValues = "NonEmpty String")
    private String newPassword;


    public UpdatePasswordRequest(String newPassword, String userId, String userName) {
        this.userId = userId;
        this.newPassword = newPassword;
        this.userName=userName;
    }

    public UpdatePasswordRequest() {
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

	@Override
	public UpdatePasswordRequest decrypt(CryptoMngrAuthService cryptoMngrAuthService) {
		return new UpdatePasswordRequest(cryptoMngrAuthService.decrypt(newPassword), 
				cryptoMngrAuthService.decrypt(userId),
				cryptoMngrAuthService.decrypt(userName)
				);
	}



}
