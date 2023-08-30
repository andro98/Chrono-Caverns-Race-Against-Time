package com.aman.payment.auth.mapper;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aman.payment.auth.model.Location;
import com.aman.payment.auth.model.dto.LocationDTO;

@Service
public class LocationMapper {

	@Autowired
	private ServiceMapper serviceMapper;
	/*
	 * location to locationDTO
	 * locations to locationDTOs
	 * ****************************************************************************************************
	 */
	public LocationDTO locationToLocationDTO(Location location) {
        return createLocationDTO(location);
    }
	
	public List<LocationDTO> locationsToLocationDTOs(List<Location> locations) {
		return locations.stream().filter(Objects::nonNull)
				.map(this::locationToLocationDTO).collect(Collectors.toList());
	}
	
	public Set<LocationDTO> locationsToLocationDTOs(Set<Location> locations) {
		return locations.stream().filter(Objects::nonNull)
				.map(this::locationToLocationDTO).collect(Collectors.toSet());
	}
	
	private LocationDTO createLocationDTO(Location location) {
		LocationDTO locationDTO = new LocationDTO();
		locationDTO.setName(location.getName());
		locationDTO.setId(String.valueOf(location.getId()));
		locationDTO.setCode(location.getCode());
		locationDTO.setStatus(location.getStatusFk());
		locationDTO.setCity(location.getCityFk().getNameEn());
		locationDTO.setCityAr(location.getCityFk().getNameAr());
		locationDTO.setCityId(String.valueOf(location.getCityFk().getId()));
		locationDTO.setDescription(location.getDescription());
		locationDTO.setNameAndCity(location.getName()+"-"+location.getCityFk().getNameAr());

		return locationDTO;

	}
	

}
