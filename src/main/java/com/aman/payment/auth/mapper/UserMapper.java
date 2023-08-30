package com.aman.payment.auth.mapper;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aman.payment.auth.model.User;
import com.aman.payment.auth.model.dto.UserWithoutPosDTO;
import com.aman.payment.auth.model.dto.UserDTO;
import com.aman.payment.auth.service.CryptoMngrAuthService;

@Service
public class UserMapper {

	@Autowired
	private CryptoMngrAuthService cryptoMngrAuthService;
	@Autowired
	private PosMapper posMapper;
	/*
	 * user to suserDTO
	 * users to userDTOs
	 * ****************************************************************************************************
	 */
	public UserDTO userToUserDTO(User user) {
        return createUserDTO(user);
    }
	
	public UserWithoutPosDTO userToLightUserDTO(User user) {
        return createUserDTOWithoutPos(user);
    }
	
	public List<UserDTO> usersToUserDTOs(List<User> users) {
		return users.stream().filter(Objects::nonNull)
				.map(this::userToUserDTO).collect(Collectors.toList());
	}
	
	
	
	public String userToUserDTOst(User user) {
        return user.toString();
    }
	
	public List<String> usersToUserDTOst(List<User> users) {
		return users.stream().filter(Objects::nonNull)
				.map(this::userToUserDTOst).collect(Collectors.toList());
	}
	
	
	
	
	public Set<UserWithoutPosDTO> usersToLightUserDTOs(Set<User> set) {
		return set.stream().filter(Objects::nonNull)
				.map(this::userToLightUserDTO).collect(Collectors.toSet());
	}
	
	
	public Set<UserDTO> usersToUserDTOs(Set<User> users) {
		return users.stream().filter(Objects::nonNull)
				.map(this::userToUserDTO).collect(Collectors.toSet());
	}
	
	private UserDTO createUserDTO(User user) {
		UserDTO userDTO = new UserDTO();
		userDTO.setActive(String.valueOf(user.getActive()));
		userDTO.setAddress(user.getAddress());
		userDTO.setEmail(user.getEmail());
		userDTO.setFirstName(user.getFirstName());
		userDTO.setGender(user.getGender());
		userDTO.setId(String.valueOf(user.getId()));
		userDTO.setImageURL(user.getImageURL()!=null?user.getImageURL():"");
		userDTO.setLastName(user.getLastName());
		userDTO.setMobile(user.getMobile());
		userDTO.setPosSet(posMapper.posesToPosDTOs(user.getPosSet()));
		userDTO.setRole(user.getRoleFk().getName());
		userDTO.setRoleId(String.valueOf(user.getRoleFk().getId()));
		userDTO.setUsername(user.getUsername());
		userDTO.setPhone(user.getPhone());
		return userDTO;
	}
	
	private UserWithoutPosDTO  createUserDTOWithoutPos(User user) {
		UserWithoutPosDTO  userDTO = new UserWithoutPosDTO();
		userDTO.setActive(String.valueOf(user.getActive()));
		userDTO.setFirstName(user.getFirstName());
		userDTO.setId(String.valueOf(user.getId()));
		userDTO.setLastName(user.getLastName());
		userDTO.setMobile(user.getMobile());
		userDTO.setRole(user.getRoleFk().getName());
		userDTO.setRoleId(String.valueOf(user.getRoleFk().getId()));
		userDTO.setUsername(user.getUsername());
		return userDTO;
	}
	
}
