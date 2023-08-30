package com.aman.payment.auth.mapper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aman.payment.auth.model.Setting;
import com.aman.payment.auth.model.dto.SettingDTO;
import com.aman.payment.auth.service.CryptoMngrAuthService;

@Service
public class SettingMapper {

	@Autowired
	private CryptoMngrAuthService cryptoMngrAuthService;
	
	
	public SettingMapper() {
		// TODO Auto-generated constructor stub
	}
	
	public SettingDTO settingToSettingDTO(Setting setting) {
        return createSettingDTO(setting);
    }
	
	 
	
	public List<SettingDTO> settingsToSettingDTOs(List<Setting> setting) {
		return setting.stream().filter(Objects::nonNull)
				.map(this::settingToSettingDTO).collect(Collectors.toList());
	}
	
	

	private SettingDTO createSettingDTO(Setting setting) {
		SettingDTO settingDTO = new SettingDTO();
		settingDTO.setId(String.valueOf(setting.getId()));
		settingDTO.setKeyOps(setting.getKeyOps());
		settingDTO.setValueOps(setting.getValueOps());
		
		return settingDTO;

	}
}
