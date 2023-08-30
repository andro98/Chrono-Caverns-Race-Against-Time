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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

public class JwtAuthenticationResponse {

	private String username;
	
	private String roleName;
	
	private ArrayList<JwtAuthServiceDTO> services = new ArrayList<JwtAuthServiceDTO>();
	
	private Set<JwtAuthPosDTO> pos = new HashSet<JwtAuthPosDTO>();
	
    private String accessToken;
    
    private String passwordModificationDate;

//    private String refreshToken;

//    private String tokenType;

    private String expiryDuration;
    
    private Set<JwtAuthSectorDTO> sectors = new HashSet<JwtAuthSectorDTO>();

//	public JwtAuthenticationResponse(String username, String roleName, ArrayList<JwtAuthServiceDTO> services, Set<JwtAuthPosDTO> pos,
//			String accessToken, String refreshToken, String expiryDuration) {
//		super();
//		this.username = username;
//		this.roleName = roleName;
//		this.services = services;
//		this.pos = pos;
//		this.accessToken = accessToken;
//		this.refreshToken = refreshToken;
//		this.expiryDuration = expiryDuration;
//	}

	public JwtAuthenticationResponse(String username, String roleName, ArrayList<JwtAuthServiceDTO> services, Set<JwtAuthPosDTO> pos,
			String accessToken, String expiryDuration, String passwordModificationDate, Set<JwtAuthSectorDTO> sectors) {
		super();
		this.username = username;
		this.roleName = roleName;
		this.services = services;
		this.pos = pos;
		this.accessToken = accessToken;
		this.expiryDuration = expiryDuration;
		this.passwordModificationDate=passwordModificationDate;
		this.sectors = sectors;
	}
	
//	public JwtAuthenticationResponse(String accessToken, String refreshToken, String expiryDuration) {
//		super();
//		this.accessToken = accessToken;
//		this.refreshToken = refreshToken;
//		this.expiryDuration = expiryDuration;
//	}


	public String getUsername() {
		return username;
	}

	public String getRoleName() {
		return roleName;
	}

	public ArrayList<JwtAuthServiceDTO> getServices() {
		return services;
	}

	public Set<JwtAuthPosDTO> getPos() {
		return pos;
	}

	public String getAccessToken() {
		return accessToken;
	}

//	public String getRefreshToken() {
//		return refreshToken;
//	}

	public String getExpiryDuration() {
		return expiryDuration;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public void setServices(ArrayList<JwtAuthServiceDTO> services) {
		this.services = services;
	}

	public void setPos(Set<JwtAuthPosDTO> pos) {
		this.pos = pos;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

//	public void setRefreshToken(String refreshToken) {
//		this.refreshToken = refreshToken;
//	}

	public void setExpiryDuration(String expiryDuration) {
		this.expiryDuration = expiryDuration;
	}
	

	public String getPasswordModificationDate() {
		return passwordModificationDate;
	}

	public void setPasswordModificationDate(String passwordModificationDate) {
		this.passwordModificationDate = passwordModificationDate;
	}

	public Set<JwtAuthSectorDTO> getSectors() {
		return sectors;
	}

	public void setSectors(Set<JwtAuthSectorDTO> sectors) {
		this.sectors = sectors;
	}

	@Override
	public String toString() {
		
		JSONObject authResponse = new JSONObject();
		try {
			authResponse.put("username", username);
			authResponse.put("roleName", roleName);
			authResponse.put("accessToken", accessToken);
			authResponse.put("expiryDuration", expiryDuration);
			authResponse.put("passwordModificationDate",passwordModificationDate);
			
			List<JSONObject> serviceList = new ArrayList<JSONObject>();

			if(services != null && !services.isEmpty()) {
				for (JwtAuthServiceDTO serviceDto : services) {

					JSONObject authServiceResponse = new JSONObject();
					
					authServiceResponse.put("id", serviceDto.getId());
					authServiceResponse.put("name", serviceDto.getName());
					authServiceResponse.put("MerchantName", serviceDto.getMerchantName());
					authServiceResponse.put("MerchantId", serviceDto.getMerchantId());
					authServiceResponse.put("tax", serviceDto.getTax());
					authServiceResponse.put("icon", serviceDto.getIcon());

					serviceList.add(authServiceResponse);

				}
			}
			authResponse.put("services", serviceList);
			
			List<JSONObject> posList = new ArrayList<JSONObject>();

			if(pos != null && !pos.isEmpty()) {
				for (JwtAuthPosDTO posDto : pos) {

					JSONObject authPosResponse = new JSONObject();
					
					authPosResponse.put("id", posDto.getId());
//					authPosResponse.put("locationName", posDto.getLocationName());
//					authPosResponse.put("locationId", posDto.getLocationId());
					authPosResponse.put("serviceId", posDto.getServiceId());
					authPosResponse.put("serviceName", posDto.getServiceName());
//					authPosResponse.put("locationCode", posDto.getLocationCode());
					authPosResponse.put("passportImageOption", posDto.getPassportImageOption());
					authPosResponse.put("permissionImageOption", posDto.getPermissionImageOption());
					authPosResponse.put("posName", posDto.getPosName());
					
					posList.add(authPosResponse);

				}
			}
			authResponse.put("pos", posList);
			
			
			List<JSONObject> sectorsList = new ArrayList<JSONObject>();

			if(sectors != null && !sectors.isEmpty()) {
				for(JwtAuthSectorDTO sector : sectors) {
						
						JSONObject authSectorResponse = new JSONObject();
						
						authSectorResponse.put("id", sector.getId());
						authSectorResponse.put("sectorName", sector.getName());
						authSectorResponse.put("locationName", sector.getLocationName());
						authSectorResponse.put("locationId", sector.getLocationId());
						sectorsList.add(authSectorResponse);
						
					}

			}
			authResponse.put("sectors", sectorsList);
			
			

		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
		return authResponse.toString();

	}
    
    
}
