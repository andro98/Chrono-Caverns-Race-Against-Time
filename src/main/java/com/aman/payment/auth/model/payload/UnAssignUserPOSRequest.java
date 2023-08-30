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

public class UnAssignUserPOSRequest implements AuthBasePayload<UnAssignUserPOSRequest>{

	private String userId;
    private String posId;



	public UnAssignUserPOSRequest(String posId, String userId) {
		super();
		this.posId = posId;
		this.userId = userId;
	}



	public UnAssignUserPOSRequest() {
    }


	
	public String getPosId() {
		return posId;
	}

	public void setPosId(String posId) {
		this.posId = posId;
	}

	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}



	@Override
	public UnAssignUserPOSRequest decrypt(CryptoMngrAuthService cryptoMngrAuthService) {
		return new UnAssignUserPOSRequest(cryptoMngrAuthService.decrypt(posId),
				cryptoMngrAuthService.decrypt(userId));
	}

	
}
