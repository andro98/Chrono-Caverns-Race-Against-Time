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

import javax.validation.constraints.NotNull;

import com.aman.payment.auth.service.CryptoMngrAuthService;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Check Auth Request", description = "The check of password request payload")
public class ValidPasswordRequest implements AuthBasePayload<ValidPasswordRequest>{

    @NotNull(message = "password cannot be blank")
    @ApiModelProperty(value = "Valid user password", required = true, allowableValues = "NonEmpty String")
    private String password;

    public ValidPasswordRequest(String password) {
        this.password = password;
    }

    public ValidPasswordRequest() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	@Override
	public ValidPasswordRequest decrypt(CryptoMngrAuthService cryptoMngrAuthService) {
		return new ValidPasswordRequest(cryptoMngrAuthService.decrypt(password));
	}

}
