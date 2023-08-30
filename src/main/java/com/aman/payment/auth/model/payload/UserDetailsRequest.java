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

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Logout request", description = "The logout request payload")
public class UserDetailsRequest {

//    @Valid
//    @NotNull(message = "Device info cannot be null")
//    @ApiModelProperty(value = "Device info", required = true, dataType = "object", allowableValues = "A valid " +
//            "deviceInfo object")
//    private DeviceInfo deviceInfo;
    
    @NotNull(message = "Details user id can be null but not blank")
    @ApiModelProperty(value = "Details user id", allowableValues = "NonEmpty Long", allowEmptyValue = false)
    private Long id;

    public UserDetailsRequest() {
    }

	public UserDetailsRequest(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
}
