package com.aman.payment.auth.management;

import java.util.List;

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

public interface UserAccountManagement {
	
	public UserPagingDTO getAllUsers(PagingRequest pagingRequest);
	
	public UserPagingDTO lookforUser(PagingSearchUserRequest pagingSearchUserRequest);
    
	public UserDTO addUser(String createdBy, AddEditUserRequest addEditUserRequest);
	
	public UserDTO editUser(String updatedBy, AddEditUserRequest addEditUserRequest);
	
	public UserDTO changePassword(UpdatePasswordRequest updatePasswordRequest);
	
	public String removePOS(long userId, String posId);
		
	public UserDTO editUserPOS(String updatedBy, AssignUserPOSRequest assignUserPOSRequest) throws Exception ;
	
	public UserDTO removeUserPOS(String updatedBy, UnAssignUserPOSRequest unAssignUserPOSRequest);
	
	public List<UserDTO> listusersForPos(PosUsersReuest posUsersReuest);
	
	public List<UserDTO> getAllUsers() throws Exception;
	
	public UserDTO changeCurrentUserPassword(UpdateCurrentUserPasswordRequest updatePasswordRequest,CustomUserDetails customUserDetails);

	public UserDTO getUserByUsername(String username);
}
