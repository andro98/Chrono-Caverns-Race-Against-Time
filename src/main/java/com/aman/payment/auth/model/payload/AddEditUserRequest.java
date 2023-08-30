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

import java.util.HashSet;
import java.util.Set;

import com.aman.payment.auth.service.CryptoMngrAuthService;
import com.aman.payment.auth.validation.annotation.NullOrNotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Registration Request", description = "The registration request payload")
public class AddEditUserRequest implements AuthBasePayload<AddEditUserRequest>{

	private String userId;
	
    @NullOrNotBlank(message = "Registration username can be null but not blank")
    @ApiModelProperty(value = "A valid username", allowableValues = "NonEmpty String")
    private String username;

    private String firstName;
    
    private String lastName;
    
    @NullOrNotBlank(message = "Registration email can be null but not blank")
    @ApiModelProperty(value = "A valid email", required = true, allowableValues = "NonEmpty String")
    private String email;
    
    private String gender;
    
    private String address;

    private String phone;
    
    private String mobile;
    
    private String imageURL;
    
    private String roleId;
    
    private String posId;
    
    private String active;
    
  //  @NotNull(message = "Registration password cannot be null")
    @ApiModelProperty(value = "A valid password string", required = true, allowableValues = "NonEmpty String")
    private String password;
    
    private Set<String> posIds;

    public AddEditUserRequest(String userId, String username, String firstName, String lastName, String email,
			String gender, String address, String phone, String mobile, String imageURL, String roleId, String active,
			String password,Set<String> posIds, String posId) {
		super();
		this.userId = userId;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.gender = gender;
		this.address = address;
		this.phone = phone;
		this.mobile = mobile;
		this.imageURL = imageURL;
		this.roleId = roleId;
		this.active = active;
	 	this.password = password;
		this.posIds = posIds;
		this.posId = posId;
	}

	public AddEditUserRequest() {
    }

	public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Set<String> getPosIds() {
		return posIds;
	}

	public void setPosIds(Set<String> posIds) {
		this.posIds = posIds;
	}

	
	public String getPosId() {
		return posId;
	}

	public void setPosId(String posId) {
		this.posId = posId;
	}

	@Override
	public AddEditUserRequest decrypt(CryptoMngrAuthService cryptoMngrAuthService) {
		Set<String> ePosIds = new HashSet<String>();
		if( posIds!=null) {
			posIds .stream().forEach(s -> {
				ePosIds.add(cryptoMngrAuthService.decrypt(s));
			});
			posIds=ePosIds;	
		}
	     
		return new AddEditUserRequest(
				userId!=null?cryptoMngrAuthService.decrypt(userId):null, 
				cryptoMngrAuthService.decrypt(username), 
				cryptoMngrAuthService.decrypt(firstName), 
				cryptoMngrAuthService.decrypt(lastName), 
				cryptoMngrAuthService.decrypt(email), 
				cryptoMngrAuthService.decrypt(gender), 
				cryptoMngrAuthService.decrypt(address), 
				cryptoMngrAuthService.decrypt(phone), 
				cryptoMngrAuthService.decrypt(mobile), 
				imageURL!=null?cryptoMngrAuthService.decrypt(imageURL):null, 
				cryptoMngrAuthService.decrypt(roleId), 
				cryptoMngrAuthService.decrypt(active), 
				password!=null?cryptoMngrAuthService.decrypt(password):null, 
				ePosIds,
				cryptoMngrAuthService.decrypt(posId));
		
	}

	
}
