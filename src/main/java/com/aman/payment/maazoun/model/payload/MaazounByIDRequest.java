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
package com.aman.payment.maazoun.model.payload;

import com.aman.payment.maazoun.service.CryptoMngrMaazounService;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Maazoun Profile Request", description = "Maazoun By Id, National Id, Card Number and Mobile")
public class MaazounByIDRequest{

    @ApiModelProperty(value = "ID", allowableValues = "NonEmpty String", allowEmptyValue = true)
    private String id;
    
    @ApiModelProperty(value = "National ID", allowableValues = "NonEmpty String", allowEmptyValue = true)
    private String nationalId;
    
    @ApiModelProperty(value = "Mobile", allowableValues = "NonEmpty String", allowEmptyValue = true)
    private String mobileNumber;
    
    @ApiModelProperty(value = "Card Number", allowableValues = "NonEmpty String", allowEmptyValue = true)
    private String cardNumber;

	public MaazounByIDRequest() {
		super();
	}

	public MaazounByIDRequest(String nationalId) {
		super();
		this.nationalId = nationalId;
	}

	

	public MaazounByIDRequest(String id, String nationalId, String mobileNumber, String cardNumber) {
		super();
		this.id = id;
		this.nationalId = nationalId;
		this.mobileNumber = mobileNumber;
		this.cardNumber = cardNumber;
	}

	
	public String getNationalId() {
		return nationalId;
	}

	public void setNationalId(String nationalId) {
		this.nationalId = nationalId;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	

}
