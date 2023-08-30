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

import com.aman.payment.auth.service.CryptoMngrAuthService;

import io.swagger.annotations.ApiModelProperty;

public class MerchantByIdRequest implements AuthBasePayload<MerchantByIdRequest>{

    @ApiModelProperty(value = "Merchant ID", allowableValues = "NonEmpty String", allowEmptyValue = false)
    private String merchantId;

	public MerchantByIdRequest(String merchantId) {
		super();
		this.merchantId = merchantId;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	@Override
	public MerchantByIdRequest decrypt(CryptoMngrAuthService cryptoMngrAuthService) {
		return new MerchantByIdRequest(cryptoMngrAuthService.decrypt(merchantId));
	}

	
   

}
