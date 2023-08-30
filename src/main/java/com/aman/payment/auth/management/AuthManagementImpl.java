package com.aman.payment.auth.management;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.aman.payment.auth.exception.UserLoginException;
import com.aman.payment.auth.mapper.PosMapper;
import com.aman.payment.auth.mapper.SectorMapper;
import com.aman.payment.auth.mapper.ServiceMapper;
import com.aman.payment.auth.model.CustomUserDetails;
import com.aman.payment.auth.model.Pos;
import com.aman.payment.auth.model.Service;
import com.aman.payment.auth.model.dto.JwtAuthPosDTO;
import com.aman.payment.auth.model.dto.JwtAuthSectorDTO;
import com.aman.payment.auth.model.dto.JwtAuthServiceDTO;
import com.aman.payment.auth.model.dto.JwtAuthenticationResponse;
import com.aman.payment.auth.model.dto.JwtExternalAuthenticationResponse;
import com.aman.payment.auth.model.payload.LoginRequest;
import com.aman.payment.auth.model.payload.ValidPasswordRequest;
import com.aman.payment.auth.security.JwtTokenProvider;
import com.aman.payment.auth.service.CryptoMngrAuthService;
import com.aman.payment.auth.service.ServiceService;
import com.aman.payment.auth.service.impl.AuthService;
import com.aman.payment.util.StatusConstant;
@Component
public class AuthManagementImpl implements AuthManagement {

	private final AuthService authService;
	private final JwtTokenProvider tokenProvider;
	private final ServiceService serviceService;
	private final PosMapper posMapper;
	private final ServiceMapper serviceMapper;
	private final SectorMapper sectorMapper;
	private final CryptoMngrAuthService cryptoMngrAuthService;

	@Autowired
	public AuthManagementImpl(AuthService authService, JwtTokenProvider tokenProvider,
			ServiceService serviceService, PosMapper posMapper, ServiceMapper serviceMapper,
			CryptoMngrAuthService cryptoMngrAuthService, SectorMapper sectorMapper) {
		super();
		this.authService = authService;
		this.tokenProvider = tokenProvider;
		this.serviceService = serviceService;
		this.posMapper = posMapper;
		this.serviceMapper = serviceMapper;
		this.cryptoMngrAuthService = cryptoMngrAuthService;
		this.sectorMapper = sectorMapper;
	}

	@Override
	public JwtAuthenticationResponse authenticateUser(LoginRequest loginRequest) {

		Authentication authentication = authService.authenticateUser(loginRequest.getUsername(), 
				loginRequest.getPassword())
				.orElseThrow(() -> new UserLoginException("Couldn't login user []"));
		
		CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

		SecurityContextHolder.getContext().setAuthentication(authentication);
//		refreshTokenService.unUthenticateOldTokensForUser(customUserDetails);
		String jwtToken = authService.generateToken(customUserDetails);
		
		Set<Pos> posSet = customUserDetails.getPosSet();
		Set<JwtAuthPosDTO> posDTOs = null;
		HashSet<JwtAuthServiceDTO> serviceDTOs = new HashSet<JwtAuthServiceDTO>();
		Set<JwtAuthSectorDTO> sectors = new HashSet<JwtAuthSectorDTO>();
		
		if(customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_AGENT) ||
				customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_AGENT_SUPERVISOR)) {
			posDTOs = new HashSet<JwtAuthPosDTO>();
			for (Pos obj : posSet) {
				serviceDTOs.addAll(serviceMapper.servicesToJwtAuthServiceDTOs(obj.getServices()));
				for(Service eService : obj.getServices()) {
					JwtAuthPosDTO dJwtAuthPosDTO = new JwtAuthPosDTO();
					dJwtAuthPosDTO.setId(cryptoMngrAuthService.encrypt(String.valueOf(obj.getId())));
//					dJwtAuthPosDTO.setLocationId(cryptoMngrAuthService.encrypt(String.valueOf(obj.getSectorFk().getLocationFk().getId())));
//					dJwtAuthPosDTO.setLocationName(cryptoMngrAuthService.encrypt(obj.getSectorFk().getLocationFk().getName()));
					dJwtAuthPosDTO.setServiceId(cryptoMngrAuthService.encrypt(String.valueOf(eService.getId())));
					dJwtAuthPosDTO.setServiceName(cryptoMngrAuthService.encrypt(eService.getName()));
					dJwtAuthPosDTO.setServiceIcon(cryptoMngrAuthService.encrypt(eService.getIcon()));
//					dJwtAuthPosDTO.setLocationCode(cryptoMngrAuthService.encrypt(obj.getSectorFk().getLocationFk().getCode()));
					dJwtAuthPosDTO.setPosName(cryptoMngrAuthService.encrypt(obj.getName()));
					dJwtAuthPosDTO.setPosId(cryptoMngrAuthService.encrypt(String.valueOf(obj.getId())));
					posDTOs.add(dJwtAuthPosDTO);
				}
				
				sectors.addAll(sectorMapper.sectorsToJwtAuthSectorDTOs(obj.getSectors()));
			}
		}else if(customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_SUPERADMIN) ||
				customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_ADMIN) ||
				customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_SUPPORT) ||
				customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_FINANCE) ||
				customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_STOREKEEPER) ||
				customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_PROSECUTOR) ||  
				customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_FINANCIAL_REVIEW)) {

			serviceDTOs.addAll(serviceMapper.servicesToJwtAuthServiceDTOs(
					serviceService.getServiceActive(StatusConstant.STATUS_ACTIVE)));
			
		}else if(customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_AREA_MANAGER) ||
				customUserDetails.getRoleFk().getName().equals(StatusConstant.ROLE_SUPERVISOR)){
			posDTOs = posMapper.posesToJwtAuthPosDTOs(posSet);
			for (Pos obj : posSet) {
				serviceDTOs.addAll(serviceMapper.servicesToJwtAuthServiceDTOs(obj.getServices()));
				sectors.addAll(sectorMapper.sectorsToJwtAuthSectorDTOs(obj.getSectors()));
			}
		}
		
		
		ArrayList<JwtAuthServiceDTO> service_array = (ArrayList<JwtAuthServiceDTO>)serviceDTOs.stream()
          .collect(Collectors.toList());
		
		return new JwtAuthenticationResponse(cryptoMngrAuthService.encrypt(loginRequest.getUsername()),
				cryptoMngrAuthService.encrypt(String.valueOf(customUserDetails.getRoleFk().getName())), 
				service_array, 
				posDTOs, 
				cryptoMngrAuthService.encrypt(jwtToken), 
				cryptoMngrAuthService.encrypt(String.valueOf(tokenProvider.getExpiryDuration())),
				customUserDetails.getPasswordModificationDate() !=null?cryptoMngrAuthService.encrypt(customUserDetails.getPasswordModificationDate().toString()):null,
						sectors);
		
	}

	@Override
	public JwtExternalAuthenticationResponse authenticateExternalUser(LoginRequest loginRequest) {

		Authentication authentication = authService.authenticateUser(loginRequest.getUsername(), 
				loginRequest.getPassword())
				.orElseThrow(() -> new UserLoginException("Couldn't login user []"));
		
		CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

		SecurityContextHolder.getContext().setAuthentication(authentication);
//		refreshTokenService.unUthenticateOldTokensForUser(customUserDetails);
		String jwtToken = authService.generateToken(customUserDetails);
		
		Set<Pos> posSet = customUserDetails.getPosSet();
		Optional<Pos> pos = posSet.stream().findFirst();

		return new JwtExternalAuthenticationResponse(customUserDetails.getUsername(),
				pos.isPresent()?String.valueOf(pos.get().getId()):"",
				cryptoMngrAuthService.encrypt(jwtToken), 
				"Bearer", 
				String.valueOf(tokenProvider.getExpiryDuration()));
		
	}
	
	@Override
	public Boolean checkUserAuthenticate(String username, ValidPasswordRequest validPasswordRequest) {
		return authService.checkUserAuthenticate(username, validPasswordRequest.getPassword());
	}


}
