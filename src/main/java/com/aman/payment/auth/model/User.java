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
package com.aman.payment.auth.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.aman.payment.core.model.audit.DateAudit;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "users", uniqueConstraints={
		 @UniqueConstraint( name = "idx_username",  columnNames ={"username"}),
		 @UniqueConstraint( name = "idx_email",  columnNames ={"email"})
})
public class User extends DateAudit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true)
    @NotNull(message = "Username can not be blank")
    private String username;

    @Column(name = "password")
    @NotNull(message = "Password cannot be null")
    private String password;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;
    
//    @NaturalId
    @Column(name = "email")
    private String email;
    
    @Column(name = "isEmailVerified", nullable = true)
    private Boolean isEmailVerified;
    
    @Column(name = "gender")
    private String gender;
    
    @Column(name = "address")
    private String address;
    
    @Column(name = "phone")
    private String phone;
    
    @Column(name = "mobile")
    private String mobile;
    
    @Column(name = "imageURL")
    private String imageURL;

    @Column(name = "active", nullable = false)
    private Boolean active;
    
    @Column(name = "isFirstTimeLogin", columnDefinition="tinyint(1) default 1")
    private Boolean isFirstTimeLogin;
    
    
    @Column(nullable = true, updatable = true)
    @Temporal(TemporalType.TIMESTAMP)
    public Date passwordModificationDate;
    
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "roleFk", referencedColumnName = "id", nullable = true)
    @JsonIgnore
    private Role roleFk;
    
	@ManyToMany
    @JoinTable(name = "user_pos",
               joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "pos_id", referencedColumnName = "id"))
    private Set<Pos> posSet = new HashSet<>();
	
    public User() {
        super();
    }

	public User(Long id) {
		super();
		this.id = id;
	}

	public User(User user) {
		super();
		id = user.getId();
		username = user.getUsername();
		password = user.getPassword();
		firstName = user.getFirstName();
		lastName = user.getLastName();
		email = user.getEmail();
		gender = user.getGender();
		address = user.getAddress();
		phone = user.getPhone();
		mobile = user.getMobile();
		imageURL = user.getImageURL();
		active = user.getActive();
		isFirstTimeLogin = user.getIsFirstTimeLogin();
		roleFk = user.getRoleFk();
		posSet = user.getPosSet();
		isEmailVerified = user.getIsEmailVerified();
		createdBy = user.getCreatedBy();
		createdAt = user.getCreatedAt();
		updatedAt = user.getUpdatedAt();
		updatedBy = user.getUpdatedBy();
		passwordModificationDate =user.getPasswordModificationDate();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Role getRoleFk() {
		return roleFk;
	}

	public void setRoleFk(Role roleFk) {
		this.roleFk = roleFk;
	}

	public Boolean getIsEmailVerified() {
		return isEmailVerified;
	}

	public void setIsEmailVerified(Boolean isEmailVerified) {
		this.isEmailVerified = isEmailVerified;
	}

	public void markVerificationConfirmed() {
		setIsEmailVerified(true);
    }

	public Set<Pos> getPosSet() {
		return posSet;
	}

	public void setPosSet(Set<Pos> posSet) {
		this.posSet = posSet;
	}

	public Boolean getIsFirstTimeLogin() {
		return isFirstTimeLogin;
	}

	public void setIsFirstTimeLogin(Boolean isFirstTimeLogin) {
		this.isFirstTimeLogin = isFirstTimeLogin;
	}
	
	

	public Date getPasswordModificationDate() {
		return passwordModificationDate;
	}

	public void setPasswordModificationDate(Date passwordModificationDate) {
		this.passwordModificationDate = passwordModificationDate;
	}

	@Override
	public int hashCode() {
		return Objects.hash(active, address, email, firstName, gender, id, imageURL, isEmailVerified, isFirstTimeLogin,
				lastName, mobile, password, phone, posSet, roleFk, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(active, other.active) && Objects.equals(address, other.address)
				&& Objects.equals(email, other.email) && Objects.equals(firstName, other.firstName)
				&& Objects.equals(gender, other.gender) && Objects.equals(id, other.id)
				&& Objects.equals(imageURL, other.imageURL) && Objects.equals(isEmailVerified, other.isEmailVerified)
				&& Objects.equals(isFirstTimeLogin, other.isFirstTimeLogin) && Objects.equals(lastName, other.lastName)
				&& Objects.equals(mobile, other.mobile) && Objects.equals(password, other.password)
				&& Objects.equals(phone, other.phone) && Objects.equals(posSet, other.posSet)
				&& Objects.equals(roleFk, other.roleFk) && Objects.equals(username, other.username);
	}
	
}
