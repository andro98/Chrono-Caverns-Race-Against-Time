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

import com.aman.payment.auth.validation.annotation.NullOrNotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Logout request", description = "The logout request payload")
public class LogOutRequest {

//    @Valid
//    @NotNull(message = "Device info cannot be null")
//    @ApiModelProperty(value = "Device info", required = true, dataType = "object", allowableValues = "A valid " +
//            "deviceInfo object")
//    private DeviceInfo deviceInfo;
    
    @NullOrNotBlank(message = "Logout Username can be null but not blank")
    @ApiModelProperty(value = "Logout username", allowableValues = "NonEmpty String", allowEmptyValue = false)
    private String username;

    public LogOutRequest() {
    }

	public LogOutRequest(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

//    public LogOutRequest(DeviceInfo deviceInfo) {
//        this.deviceInfo = deviceInfo;
//    }
//
//    public DeviceInfo getDeviceInfo() {
//        return deviceInfo;
//    }
//
//    public void setDeviceInfo(DeviceInfo deviceInfo) {
//        this.deviceInfo = deviceInfo;
//    }
}
