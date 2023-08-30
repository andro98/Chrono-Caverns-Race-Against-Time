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

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "SubService Request", description = "SubService By main service id")
public class SubServiceByServiceIDRequest implements AuthBasePayload<SubServiceByServiceIDRequest>{

    @ApiModelProperty(value = "Service ID", allowableValues = "NonEmpty String", allowEmptyValue = false)
    private String serviceId;
    
	public SubServiceByServiceIDRequest() {

	}

	public SubServiceByServiceIDRequest(String serviceId) {
		super();
		this.serviceId = serviceId;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	@Override
	public SubServiceByServiceIDRequest decrypt(CryptoMngrAuthService cryptoMngrAuthService) {
		return new SubServiceByServiceIDRequest(cryptoMngrAuthService.decrypt(serviceId));
	}

	

}
