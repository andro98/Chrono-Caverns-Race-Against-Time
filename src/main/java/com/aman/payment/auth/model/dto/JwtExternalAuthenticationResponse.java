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
package com.aman.payment.auth.model.dto;

import org.json.JSONException;
import org.json.JSONObject;

public class JwtExternalAuthenticationResponse {

	private String username;
	private String posId;
	private String accessToken;
//    private String refreshToken;
	private String tokenType;
	private String expiryDuration;

	public JwtExternalAuthenticationResponse(String username, String posId, String accessToken, String tokenType,
			String expiryDuration) {
		super();
		this.username = username;
		this.posId = posId;
		this.accessToken = accessToken;
		this.tokenType = tokenType;
		this.expiryDuration = expiryDuration;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public String getExpiryDuration() {
		return expiryDuration;
	}

	public void setExpiryDuration(String expiryDuration) {
		this.expiryDuration = expiryDuration;
	}

	public String getPosId() {
		return posId;
	}

	public void setPosId(String posId) {
		this.posId = posId;
	}

	@Override
	public String toString() {

		JSONObject authResponse = new JSONObject();
		try {
			authResponse.put("username", username);
			authResponse.put("posId", posId);
			authResponse.put("accessToken", accessToken);
			authResponse.put("tokenType", tokenType);
			authResponse.put("expiryDuration", expiryDuration);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return authResponse.toString();

	}

}
