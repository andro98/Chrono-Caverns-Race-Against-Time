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

public class AssignUserPOSRequest implements AuthBasePayload<AssignUserPOSRequest>{

	private String userId;
    private String posId;
    private String role;
    private String userName;


	public AssignUserPOSRequest(String posId, String userId, String role, String userName) {
		super();
		this.posId = posId;
		this.userId = userId;
		this.role = role;
		this.userName = userName;
	}

	public AssignUserPOSRequest() {
    }

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPosId() {
		return posId;
	}

	public void setPosId(String posId) {
		this.posId = posId;
	}
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public AssignUserPOSRequest decrypt(CryptoMngrAuthService cryptoMngrAuthService) {
		return new AssignUserPOSRequest(cryptoMngrAuthService.decrypt(posId),
				cryptoMngrAuthService.decrypt(userId),
				cryptoMngrAuthService.decrypt(role),
				cryptoMngrAuthService.decrypt(userName));
	}

	
}
