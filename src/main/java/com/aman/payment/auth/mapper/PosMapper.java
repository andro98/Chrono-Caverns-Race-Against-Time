package com.aman.payment.auth.mapper;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aman.payment.auth.model.Pos;
import com.aman.payment.auth.model.Sector;
import com.aman.payment.auth.model.User;
import com.aman.payment.auth.model.dto.JwtAuthPosDTO;
import com.aman.payment.auth.model.dto.PosDTO;
import com.aman.payment.auth.model.dto.PosWithUserAgentDTO;
import com.aman.payment.auth.model.dto.SectorDTO;
import com.aman.payment.auth.service.CryptoMngrAuthService;
import com.aman.payment.util.StatusConstant;

@Service
public class PosMapper {

	@Autowired
	private ServiceMapper serviceMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private SectorMapper sectorMapper;
	
	@Autowired
	private CryptoMngrAuthService cryptoMngrAuthService;

	
	/*
	 * pos to posDTO
	 * poses to posDTOs
	 * ****************************************************************************************************
	 */
	public PosWithUserAgentDTO posToLightPosDTO(Pos pos) {
        return createLightPosDTO(pos);
    }
	
	public PosDTO posToPosDTO(Pos pos) {
        return createPosDTO(pos);
    }
	
	public List<PosDTO> posesToPosDTOs(List<Pos> poses) {
		return poses.stream().filter(Objects::nonNull)
				.map(this::posToPosDTO).collect(Collectors.toList());
	}
	
	public Set<PosDTO> posesToPosDTOs(Set<Pos> poses) {
		return poses != null ? poses.stream().filter(Objects::nonNull)
				.map(this::posToPosDTO).collect(Collectors.toSet()) : null;
	}
	
	private PosDTO createPosDTO(Pos pos) {
		PosDTO posDTO = new PosDTO();
		posDTO.setName(pos.getName());
		posDTO.setId(String.valueOf(pos.getId()));
		posDTO.setCode(pos.getCode());
		posDTO.setStatus(pos.getStatusFk());
//		posDTO.setLocation(pos.getSectorFk().getLocationFk().getName());
//		posDTO.setLocation_code(pos.getSectorFk().getLocationFk().getCode());
//		posDTO.setLocation_id(String.valueOf(pos.getSectorFk().getLocationFk().getId()));
//		posDTO.setSectorId(String.valueOf(pos.getSectorFk().getId()));
//		posDTO.setSectorName(pos.getSectorFk().getName());
		posDTO.setServices(serviceMapper.servicesToServiceDTOs(pos.getServices()));
		posDTO.setUsers(userMapper.usersToLightUserDTOs(pos.getUsers()));
		posDTO.setSectors(sectorMapper.sectorsToSectorDTOs(pos.getSectors()));
		return posDTO;
	}
	
	private PosWithUserAgentDTO createLightPosDTO(Pos pos) {
		PosWithUserAgentDTO posDTO = new PosWithUserAgentDTO();
		posDTO.setName(pos.getName());
		posDTO.setId(String.valueOf(pos.getId()));
		posDTO.setCode(pos.getCode());
		posDTO.setStatus(pos.getStatusFk());
//		posDTO.setLocation(pos.getSectorFk().getLocationFk().getName());
//		posDTO.setLocation_code(pos.getSectorFk().getLocationFk().getCode());
//		posDTO.setLocation_id(String.valueOf(pos.getSectorFk().getLocationFk().getId()));
//		posDTO.setSectorId(String.valueOf(pos.getSectorFk().getId()));
//		posDTO.setSectorName(pos.getSectorFk().getName());
		posDTO.setUsers(userMapper.usersToLightUserDTOs(FitterUserRole (pos.getUsers())));
		posDTO.setSectors(sectorMapper.sectorsToSectorDTOs(pos.getSectors()));
		return posDTO;
	}
	
	private Set<User>  FitterUserRole(Set<User> set){
		Set<User> users= new HashSet<User>();
		for( User user : set) {
			if(user.getRoleFk().getName().equals(StatusConstant.ROLE_AGENT) ||
					user.getRoleFk().getName().equals(StatusConstant.ROLE_AGENT_SUPERVISOR)) {
				users.add(user);
				
			}
		}
		
		return users;
	}
	
	
	
	/*
	 * pos to jwtAuthPosDTO
	 * poses to jwtAuthPosDTOs
	 * ****************************************************************************************************
	 */
	public JwtAuthPosDTO posToJwtAuthPosDTO(Pos pos) {
        return createJwtAuthPosDTO(pos);
    }
	
	public List<JwtAuthPosDTO> posesToJwtAuthPosDTOs(List<Pos> poses) {
		return poses.stream().filter(Objects::nonNull)
				.map(this::posToJwtAuthPosDTO).collect(Collectors.toList());
	}
	
	public Set<JwtAuthPosDTO> posesToJwtAuthPosDTOs(Set<Pos> poses) {
		return poses.stream().filter(Objects::nonNull)
				.map(this::posToJwtAuthPosDTO).collect(Collectors.toSet());
	}
	
	private JwtAuthPosDTO createJwtAuthPosDTO(Pos pos) {
		JwtAuthPosDTO jwtAuthPosDTO = new JwtAuthPosDTO();
		jwtAuthPosDTO.setId(cryptoMngrAuthService.encrypt(String.valueOf(pos.getId())));
//		jwtAuthPosDTO.setLocationName (cryptoMngrAuthService.encrypt(pos.getSectorFk().getLocationFk().getName()));
//		jwtAuthPosDTO.setLocationId(cryptoMngrAuthService.encrypt(pos.getSectorFk().getLocationFk().getId().toString()));
//		jwtAuthPosDTO.setLocationCode (cryptoMngrAuthService.encrypt(pos.getSectorFk().getLocationFk().getCode()));
		jwtAuthPosDTO.setPosName(cryptoMngrAuthService.encrypt(pos.getName()));
//		jwtAuthPosDTO.setSectorId(String.valueOf(pos.getSectorFk().getId()));
//		jwtAuthPosDTO.setSectorName(pos.getSectorFk().getName());
//		jwtAuthPosDTO.setCityId(cryptoMngrAuthService.encrypt(pos.getSectorFk().getLocationFk().getCityFk().getId().toString()));
//		jwtAuthPosDTO.setCityName(cryptoMngrAuthService.encrypt(pos.getSectorFk().getLocationFk().getCityFk().getNameAr()));
		jwtAuthPosDTO.setSectors(sectorMapper.sectorsToSectorDTOs(pos.getSectors()));
		return jwtAuthPosDTO;
	}
}
