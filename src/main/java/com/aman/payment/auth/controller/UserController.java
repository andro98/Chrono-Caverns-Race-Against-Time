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
package com.aman.payment.auth.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aman.payment.annotation.CurrentUser;
import com.aman.payment.auth.management.UserAccountManagement;
import com.aman.payment.auth.model.CustomUserDetails;
import com.aman.payment.auth.model.dto.UserDTO;
import com.aman.payment.auth.model.dto.UserPagingDTO;
import com.aman.payment.auth.model.payload.AddEditUserRequest;
import com.aman.payment.auth.model.payload.AssignUserPOSRequest;
import com.aman.payment.auth.model.payload.PagingRequest;
import com.aman.payment.auth.model.payload.PagingSearchUserRequest;
import com.aman.payment.auth.model.payload.PosUsersReuest;
import com.aman.payment.auth.model.payload.UnAssignUserPOSRequest;
import com.aman.payment.auth.model.payload.UpdateCurrentUserPasswordRequest;
import com.aman.payment.auth.model.payload.UpdatePasswordRequest;
import com.aman.payment.auth.service.CryptoMngrAuthService;
import com.aman.payment.util.StatusConstant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
@Api(value = "User Rest API", description = "Defines endpoints for the logged in user. It's secured by default")

public class UserController {

	private static final Logger logger = Logger.getLogger(UserController.class);

	private final UserAccountManagement userAccountManagement;
	private final CryptoMngrAuthService cryptoMngrAuthService;

	@Autowired
	public UserController(UserAccountManagement userAccountManagement, CryptoMngrAuthService cryptoMngrAuthService) {
		this.userAccountManagement = userAccountManagement;
		this.cryptoMngrAuthService = cryptoMngrAuthService;
	}

	/**
	 * Returns all users info in the system. Requires Admin access
	 */
	@PostMapping("/users")
//    @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of configured users info. Requires ADMIN Access")
	public ResponseEntity<UserPagingDTO> getAllUsersInfo(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody PagingRequest pagingRequest) {
		
		PagingRequest decryptPagingRequest = 
				pagingRequest.decrypt(cryptoMngrAuthService);
		
		UserPagingDTO userPagingDTO = userAccountManagement.getAllUsers(decryptPagingRequest);
		List<String> eUserDTO = new ArrayList<String>();
		userPagingDTO.getResults().stream().forEach(s -> {
			if(customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_SUPERADMIN)) {
				eUserDTO.add(cryptoMngrAuthService.encrypt(s.toString()));
			}else {
				if(!s.getRole().equals(StatusConstant.ROLE_SUPERADMIN))
					eUserDTO.add(cryptoMngrAuthService.encrypt(s.toString()));
			}
			
		});
		userPagingDTO.setResults(null);
		userPagingDTO.setTransactions(eUserDTO);
		
		return ResponseEntity.ok(userPagingDTO);
	}
	
	@GetMapping("/allUsers")
	@ApiOperation(value = "Returns the list of configured users info. Requires ADMIN Access")
	public ResponseEntity<List<String>> getAllUsers(@CurrentUser CustomUserDetails customUserDetails) throws Exception {
		List<UserDTO> userDtos = userAccountManagement.getAllUsers();
		
		List<String> eUserDTO = new ArrayList<String>();
		userDtos.stream().forEach(s -> {
			if(customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_SUPERADMIN)) {
				eUserDTO.add(cryptoMngrAuthService.encrypt(s.toString()));
			}else {
				if(!s.getRole().equals(StatusConstant.ROLE_SUPERADMIN))
					eUserDTO.add(cryptoMngrAuthService.encrypt(s.toString()));
			}
		});
		
		return ResponseEntity.ok(eUserDTO);
	}

	/**
	 * Returns all users info in the system. Requires Admin access
	 */
	@PostMapping("/lookforUser")
//    @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of configured users info. Requires ADMIN Access")
	public ResponseEntity<UserPagingDTO> lookforUser(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody PagingSearchUserRequest pagingSearchUserRequest) {
		
		PagingSearchUserRequest decryptPagingSearchUserRequest = 
				pagingSearchUserRequest.decrypt(cryptoMngrAuthService);
		
		UserPagingDTO userPagingDTO = userAccountManagement.lookforUser(decryptPagingSearchUserRequest);
		List<String> eUserDTO = new ArrayList<String>();
		if(userPagingDTO.getResults() != null) {
			userPagingDTO.getResults().stream().forEach(s -> {
				if(customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_SUPERADMIN)) {
					eUserDTO.add(cryptoMngrAuthService.encrypt(s.toString()));
				}else {
					if(!s.getRole().equals(StatusConstant.ROLE_SUPERADMIN))
						eUserDTO.add(cryptoMngrAuthService.encrypt(s.toString()));
				}
			});
			userPagingDTO.setTransactions(eUserDTO);
		}
		
		return ResponseEntity.ok(userPagingDTO);
	}
	
	@PostMapping("/usersForPos")
//  @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Returns the list of configured users related by pos")
	public ResponseEntity<List<UserDTO>> getAllUsersInfo(@Valid @RequestBody PosUsersReuest posUsersReuest) {
		
		PosUsersReuest decryptposUsersReuest =posUsersReuest.decrypt(cryptoMngrAuthService);
		
		List<UserDTO> users= userAccountManagement.listusersForPos(decryptposUsersReuest);
		 
		
		return ResponseEntity.ok(users);
	}
	
	/**
	 * Entry point for the user registration process. On successful registration,
	 * publish an event to generate email verification token
	 */
	@PostMapping("/register")
	@ApiOperation(value = "Registers the user and publishes an event to generate the email verification")
	public ResponseEntity<String> registerUser(@CurrentUser CustomUserDetails customUserDetails,
			@ApiParam(value = "The AddEditUserRequest payload") @Valid @RequestBody AddEditUserRequest addEditUserRequest) {

		AddEditUserRequest decryptAddEditUserRequest = addEditUserRequest.decrypt(cryptoMngrAuthService);

		return ResponseEntity
				.ok(cryptoMngrAuthService.encrypt(userAccountManagement.
						addUser(customUserDetails.getUsername(), decryptAddEditUserRequest).toString()));
	}

	/**
	 * Returns all users info in the system. Requires Admin access
	 */
	@PostMapping("/update")
//    @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Update User Info. Requires ADMIN Access")
	public ResponseEntity<String> updateUsersInfo(@CurrentUser CustomUserDetails customUserDetails,
			@Valid @RequestBody AddEditUserRequest addEditUserRequest) {

		AddEditUserRequest decryptAddEditUserRequest = addEditUserRequest.decrypt(cryptoMngrAuthService);

		return ResponseEntity
				.ok(cryptoMngrAuthService.encrypt(userAccountManagement.
						editUser(customUserDetails.getUsername(), decryptAddEditUserRequest).toString()));
	}
	
	@PostMapping("/assignUserPos")
	public ResponseEntity<String> assignUserPos(@CurrentUser CustomUserDetails customUserDetails, @Valid @RequestBody AssignUserPOSRequest assignUserPos) throws Exception {

		assignUserPos = assignUserPos.decrypt(cryptoMngrAuthService);
		String result = null;
		try {
			result = cryptoMngrAuthService.encrypt(userAccountManagement.editUserPOS(customUserDetails.getUsername(), assignUserPos).toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return ResponseEntity.ok(result);		
	}
	
	
	
	
	@DeleteMapping("/unAssignUserPos")
	public ResponseEntity<String> unAssignUserPos(@CurrentUser CustomUserDetails customUserDetails,@Valid @RequestBody UnAssignUserPOSRequest unAssignUserPOSRequest) {
 	unAssignUserPOSRequest = unAssignUserPOSRequest.decrypt(cryptoMngrAuthService);
 	
 	return ResponseEntity.ok(cryptoMngrAuthService.encrypt(userAccountManagement.
			removeUserPOS(customUserDetails.getUsername(), unAssignUserPOSRequest).toString()));

	}

	/**
	 * Updates the password of the current logged in user
	 */
	@PostMapping("/update/password")
//    @PreAuthorize("hasRole('USER')")
	@ApiOperation(value = "Allows the user to change his password once logged in by supplying the correct current "
			+ "password")
	public ResponseEntity<String> updatePassword(@CurrentUser CustomUserDetails customUserDetails,
			@ApiParam(value = "The UpdatePasswordRequest payload") @Valid @RequestBody UpdatePasswordRequest updatePasswordRequest) {

		UpdatePasswordRequest decryptUpdatePasswordRequest = updatePasswordRequest.decrypt(cryptoMngrAuthService);

		return ResponseEntity.ok(cryptoMngrAuthService.encrypt(
				userAccountManagement.changePassword(decryptUpdatePasswordRequest).toString()));
	}
	
	@PostMapping("/update/password/currentUser")
//  @PreAuthorize("hasRole('USER')")
	@ApiOperation(value = "Allows the user to change his password once logged in by supplying the correct current "
			+ "password")
	public ResponseEntity<String> updatePasswordForCurrentUser(@CurrentUser CustomUserDetails customUserDetails,
			@ApiParam(value = "The UpdatePasswordRequest payload") @Valid @RequestBody UpdateCurrentUserPasswordRequest updatePasswordRequest) {

		UpdateCurrentUserPasswordRequest decryptUpdatePasswordRequest = updatePasswordRequest.decrypt(cryptoMngrAuthService);

		return ResponseEntity.ok(cryptoMngrAuthService.encrypt(
				userAccountManagement.changeCurrentUserPassword(decryptUpdatePasswordRequest,customUserDetails).toString()));
	}

}
