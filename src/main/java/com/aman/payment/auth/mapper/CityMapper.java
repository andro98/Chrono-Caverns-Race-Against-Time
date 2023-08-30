package com.aman.payment.auth.mapper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aman.payment.auth.model.City;
import com.aman.payment.auth.model.dto.CityDTO;
import com.aman.payment.auth.service.CryptoMngrAuthService;

@Service
public class CityMapper {
	/*
	 * city to cityDTO
	 * city to cityDTOs
	 * ****************************************************************************************************
	 */
	public CityDTO cityToCityDTO(City city) {
        return createCityDTO(city);
    }
	
	public List<CityDTO> locationsToLocationDTOs(List<City> cities) {
		return cities.stream().filter(Objects::nonNull)
				.map(this::cityToCityDTO).collect(Collectors.toList());
	}
	
	private CityDTO createCityDTO(City city) {
		CityDTO cityDTO = new CityDTO();
		cityDTO.setName(city.getNameEn());
		cityDTO.setId(String.valueOf(city.getId()));
		cityDTO.setNameAr(city.getNameAr());
		return cityDTO;
	}
	

}
