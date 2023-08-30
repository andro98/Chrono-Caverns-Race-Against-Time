package com.aman.payment.auth.management;

import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.aman.payment.auth.mapper.UserMapper;
import com.aman.payment.auth.model.CustomUserDetails;
import com.aman.payment.auth.model.Pos;
import com.aman.payment.auth.model.Role;
import com.aman.payment.auth.model.User;
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
import com.aman.payment.auth.service.PosService;
import com.aman.payment.auth.service.RoleService;
import com.aman.payment.auth.service.impl.UserService;
import com.aman.payment.core.exception.ResourceAlreadyInUseException;
import com.aman.payment.util.StatusConstant;

@Component
public class UserAccountManagementImpl implements UserAccountManagement {

	private final UserService userService;
	private final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder;
	private final PosService posService;
	private final RoleService roleService;

	@Autowired
	public UserAccountManagementImpl(UserService userService, UserMapper userMapper,
			CryptoMngrAuthService cryptoMngrAuthService, PasswordEncoder passwordEncoder, PosService posService,
			RoleService roleService) {
		super();
		this.userService = userService;
		this.userMapper = userMapper;
		this.passwordEncoder = passwordEncoder;
		this.posService = posService;
		this.roleService=roleService;
	}

	@Override
	public UserPagingDTO getAllUsers(PagingRequest pagingRequest) {

		Pageable pageable = PageRequest.of(Integer.valueOf(pagingRequest.getPageNo()),
				Integer.valueOf(pagingRequest.getPageSize()));

		Page<User> pageResult = userService.findAll(pageable);

		UserPagingDTO userPagingDTO = new UserPagingDTO();
		userPagingDTO.setCount(pageResult.getTotalElements());
		userPagingDTO.setTotalPages(pageResult.getTotalPages());
		userPagingDTO.setResults(userMapper.usersToUserDTOs(pageResult.getContent()));

		return userPagingDTO;

	}

	@Override
	public List<UserDTO> getAllUsers() throws Exception {
		List<User> users = userService.findAll();
		List<UserDTO> userDtos = userMapper.usersToUserDTOs(users);
		return userDtos;

	}

	@Override
	public UserDTO addUser(String createdBy, AddEditUserRequest addEditUserRequest) {
		User user = new User();
		user.setActive(Boolean.parseBoolean(addEditUserRequest.getActive()));
		user.setAddress(addEditUserRequest.getAddress());
		user.setCreatedAt(Date.from(Instant.now()));
		user.setCreatedBy(createdBy);
		user.setEmail(addEditUserRequest.getEmail());
		user.setFirstName(addEditUserRequest.getFirstName());
		user.setGender(addEditUserRequest.getGender());
		user.setImageURL(addEditUserRequest.getImageURL());
		user.setLastName(addEditUserRequest.getLastName());
		user.setMobile(addEditUserRequest.getMobile());
		user.setPassword(passwordEncoder.encode(addEditUserRequest.getPassword()));
		user.setPhone(addEditUserRequest.getPhone());
		user.setRoleFk(new Role(Long.parseLong(addEditUserRequest.getRoleId())));
		user.setUsername(addEditUserRequest.getUsername().trim());
		user.setIsFirstTimeLogin(true);
		user.setPasswordModificationDate(Date.from(Instant.now()));

		if (userService.existsByUsername(addEditUserRequest.getUsername())) {
			throw new ResourceAlreadyInUseException("Username already exist", "", addEditUserRequest.getUsername());
		}

		return userMapper.userToUserDTO(userService.save(user));
	}

	@Override
	public UserDTO editUser(String updatedBy, AddEditUserRequest addEditUserRequest) {
		User user = userService.findById(Long.parseLong(addEditUserRequest.getUserId())).get();
		// user.setId(Long.parseLong(cryptoMngrAuthService.decrypt(userId)));
		user.setActive(Boolean.parseBoolean(addEditUserRequest.getActive()));
		user.setAddress(addEditUserRequest.getAddress());
		user.setUpdatedAt(Date.from(Instant.now()));
		user.setUpdatedBy(updatedBy);
		user.setEmail(addEditUserRequest.getEmail());
		user.setFirstName(addEditUserRequest.getFirstName());
		user.setGender(addEditUserRequest.getGender());
		user.setImageURL(addEditUserRequest.getImageURL());
		user.setLastName(addEditUserRequest.getLastName());
		user.setMobile(addEditUserRequest.getMobile());
		user.setPhone(addEditUserRequest.getPhone());
		Role role =roleService.findById(Long.parseLong(addEditUserRequest.getRoleId())).get();
		user.setRoleFk(role);
//		Set<Pos> posSet = new HashSet<Pos>();
//		for(String posId : addEditUserRequest.getPosIds()) {
//			posSet.add(new Pos(Long.parseLong(posId)));
//		}
//		user.setPosSet(posSet);

		return userMapper.userToUserDTO(userService.save(user));
	}

	@Override
	public UserDTO editUserPOS(String updatedBy, AssignUserPOSRequest assignUserPOSRequest) throws Exception {

		User user = userService.findById(Long.parseLong(assignUserPOSRequest.getUserId())).get();
		Set<Pos> posSet = user.getPosSet();
		if (user.getRoleFk().getName().equals(StatusConstant.ROLE_SUPERVISOR)
				|| user.getRoleFk().getName().equals(StatusConstant.ROLE_AREA_MANAGER)
				|| user.getRoleFk().getName().equals(StatusConstant.ROLE_INTEGRATION)) {

			Pos existPos = posSet.stream().filter(x -> Long.valueOf(assignUserPOSRequest.getPosId()) == x.getId())
					.findAny().orElse(null);
			if (existPos == null) {
				Pos pos = posService.getPOSById(Long.valueOf(assignUserPOSRequest.getPosId()));
				userService.addPosToUser(user.getId(), Long.valueOf(assignUserPOSRequest.getPosId()));
				user.getPosSet().add(pos);
				return userMapper.userToUserDTO(user);
			}
		} else if (user.getRoleFk().getName().equals(StatusConstant.ROLE_AGENT) ||
				user.getRoleFk().getName().equals(StatusConstant.ROLE_AGENT_SUPERVISOR)) {
			List<String> users = posService.getAgentUserForPOS(Long.valueOf(assignUserPOSRequest.getPosId()));
			if (users != null && users.size() > 0) {
				throw new Exception("Selected POS has been taken by user '" + users.get(0) + "'");
			} else {
				posSet.add(posService.getPOSById(Long.valueOf(assignUserPOSRequest.getPosId())));
				user.setPosSet(posSet);
				return userMapper.userToUserDTO(userService.save(user));
			}
		}

		return userMapper.userToUserDTO(user);
	}

	@Override
	public UserDTO removeUserPOS(String updatedBy, UnAssignUserPOSRequest unAssignUserPOSRequest) {
		User user = userService.findById(Long.parseLong(unAssignUserPOSRequest.getUserId())).get();
		Set<Pos> newPosSet = new HashSet<Pos>();

		Set<Pos> posSet = user.getPosSet();
		String posId = unAssignUserPOSRequest.getPosId();
		// posSet.add(new Pos(Long.parseLong(posId)));

		for (Pos pos : posSet) {
			if (pos.getId() != Long.parseLong(posId)) {
				newPosSet.add(pos);

			}
		}

		posSet = new HashSet<Pos>();

		user.setPosSet(newPosSet);
		return userMapper.userToUserDTO(userService.save(user));
	}

	@Override
	public UserDTO changePassword(UpdatePasswordRequest updatePasswordRequest) {
		User user = userService.findByUsername(updatePasswordRequest.getUserName()).get();

		user.setPassword(passwordEncoder.encode(updatePasswordRequest.getNewPassword()));
		user.setPasswordModificationDate(Date.from(Instant.now()));

		return userMapper.userToUserDTO(userService.save(user));

	}

	@Override
	public UserPagingDTO lookforUser(PagingSearchUserRequest pagingSearchUserRequest) {

		Pageable pageable = PageRequest.of(Integer.valueOf(pagingSearchUserRequest.getPageNo()),
				Integer.valueOf(pagingSearchUserRequest.getPageSize()));

		if (pagingSearchUserRequest.getSearchBy() == null || pagingSearchUserRequest.getSearchBy().isEmpty()) {
			return new UserPagingDTO();
		} else {
			Page<User> pageResult = userService.lookforUser(pagingSearchUserRequest.getSearchBy(), pageable);

			UserPagingDTO userPagingDTO = new UserPagingDTO();
			userPagingDTO.setCount(pageResult.getTotalElements());
			userPagingDTO.setTotalPages(pageResult.getTotalPages());
			userPagingDTO.setResults(userMapper.usersToUserDTOs(pageResult.getContent()));

			return userPagingDTO;
		}

	}

	public String removePOS(long userId, String posId) {
		try {
			userService.deleteUserPOS(userId, Long.parseLong(posId));
			return "Success";

		} catch (Exception e) {
			// TODO: handle exception
			return "faild";

		}
	}

	@Override
	public List<UserDTO> listusersForPos(PosUsersReuest posUsersReuest) {
		// TODO Auto-generated method stub
		return userMapper.usersToUserDTOs(userService.getPosUsers(Long.parseLong(posUsersReuest.getPosId())));
	}

	@Override
	public UserDTO changeCurrentUserPassword(UpdateCurrentUserPasswordRequest updatePasswordRequest,
			CustomUserDetails customUserDetails) {
		User user = userService.findByUsername(customUserDetails.getUsername()).get() ;
		
		BCryptPasswordEncoder b = new BCryptPasswordEncoder();
		if(b.matches(updatePasswordRequest.getOldPassword(), user.getPassword()))
//		if (
//				passwordEncoder.encode(updatePasswordRequest.getOldPassword()) != null && 
//				passwordEncoder.encode(updatePasswordRequest.getOldPassword())
//				
//				.equals(customUserDetails.getPassword()))
			{
			
		//	passwordEncoder.encode(updatePasswordRequest.getOldPassword());
			user.setPassword(passwordEncoder.encode(updatePasswordRequest.getNewPassword()));
			user.setPasswordModificationDate(Date.from(Instant.now()));

			return userMapper.userToUserDTO(userService.save(user));
		}
		return null;

	}

	@Override
	public UserDTO getUserByUsername(String username) {
		Optional<User> user = userService.findByUsername(username);
		if(user.isPresent()) {
			return userMapper.userToUserDTO(user.get());
		}
		return null;
	}

}
