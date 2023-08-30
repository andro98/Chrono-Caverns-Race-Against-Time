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

public class PosByIDRequest implements AuthBasePayload<PosByIDRequest> {

	private String posId;

	public PosByIDRequest() {
		super();
	}

	public PosByIDRequest(String posId) {
		super();
		this.posId = posId;
	}

	public String getPosId() {
		return posId;
	}

	public void setPosId(String posId) {
		this.posId = posId;
	}

	@Override
	public PosByIDRequest decrypt(CryptoMngrAuthService cryptoMngrAuthService) {
		return new PosByIDRequest(cryptoMngrAuthService.decrypt(posId));
	}

}
