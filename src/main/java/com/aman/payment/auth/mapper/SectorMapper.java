package com.aman.payment.auth.mapper;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aman.payment.auth.model.Sector;
import com.aman.payment.auth.model.dto.JwtAuthSectorDTO;
import com.aman.payment.auth.model.dto.SectorDTO;
import com.aman.payment.auth.service.CryptoMngrAuthService;

@Service
public class SectorMapper {

	/*
	 * sector to sectorDTO
	 * sector to sectorDTOs
	 * ****************************************************************************************************
	 */
	@Autowired
	private CryptoMngrAuthService cryptoMngrAuthService;
	
	public SectorDTO sectorToSectorDTO(Sector sector) {
        return createSectorDTO(sector);
    }
	
	public JwtAuthSectorDTO sectorToJwtAuthSectorDTO(Sector sector) {
        return createJwtAuthSectorDTO(sector);
    }
	
	public List<SectorDTO> sectorsToSectorDTOs(List<Sector> sectors) {
		return sectors.stream().filter(Objects::nonNull)
				.map(this::sectorToSectorDTO).collect(Collectors.toList());
	}
	
	public List<JwtAuthSectorDTO> sectorsToJwtAuthSectorDTOs(List<Sector> sectors) {
		return sectors.stream().filter(Objects::nonNull)
				.map(this::sectorToJwtAuthSectorDTO).collect(Collectors.toList());
	}
	
	public Set<SectorDTO> sectorsToSectorDTOs(Set<Sector> sectors) {
		return sectors.stream().filter(Objects::nonNull)
				.map(this::sectorToSectorDTO).collect(Collectors.toSet());
	}
	
	public Set<JwtAuthSectorDTO> sectorsToJwtAuthSectorDTOs(Set<Sector> sectors) {
		return sectors.stream().filter(Objects::nonNull)
				.map(this::sectorToJwtAuthSectorDTO).collect(Collectors.toSet());
	}
	
	private SectorDTO createSectorDTO(Sector sector) {
		SectorDTO sectorDTO = new SectorDTO();
		sectorDTO.setName(sector.getName());
		sectorDTO.setId(String.valueOf(sector.getId()));
		sectorDTO.setStatus(sector.getStatusFk());
		sectorDTO.setLocationId(String.valueOf(sector.getLocationFk().getId()));
		sectorDTO.setLocationName(sector.getLocationFk().getName());
		
		return sectorDTO;

	}
	
	private JwtAuthSectorDTO createJwtAuthSectorDTO(Sector sector) {
		JwtAuthSectorDTO jwtAuthSectorDTO = new JwtAuthSectorDTO();
		jwtAuthSectorDTO.setName(sector.getName());
		jwtAuthSectorDTO.setId(String.valueOf(sector.getId()));
		jwtAuthSectorDTO.setLocationId(String.valueOf(sector.getLocationFk().getId()));
		jwtAuthSectorDTO.setLocationName(sector.getLocationFk().getName()+"-"+sector.getLocationFk().getCityFk().getNameAr());
		
		return jwtAuthSectorDTO.encrypt(cryptoMngrAuthService);

	}
	

}
