package com.aman.payment.maazoun.mapper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.aman.payment.maazoun.model.MaazounBookDeliverInfo;
import com.aman.payment.maazoun.model.dto.MaazounDeliverInfoDTO;

@Service
public class MaazounDeliverInfoMapper {

	/*
	 * MaazounDelivernfo to MaazounDelivernfoDTO
	 * MaazounDelivernfos to MaazounDelivernfoDTOs
	 * ****************************************************************************************************
	 */
	public MaazounDeliverInfoDTO maazounDeliverinfoToMaazounDelivernfoDTO(MaazounBookDeliverInfo maazounBookDeliverInfo) {
		if (maazounBookDeliverInfo!=null) {
	        return createMaazounDeliverInfoDTO(maazounBookDeliverInfo);

		}
		else return null ;
    }
	
	public List<MaazounDeliverInfoDTO> maazounDeliverinfosToMaazounDelivernfoDTOs(List<MaazounBookDeliverInfo> maazounBookDeliverInfoSet) {
		return maazounBookDeliverInfoSet.stream().filter(Objects::nonNull)
				.map(this::maazounDeliverinfoToMaazounDelivernfoDTO).collect(Collectors.toList());
	}
	
	private MaazounDeliverInfoDTO createMaazounDeliverInfoDTO(MaazounBookDeliverInfo maazounBookDeliverInfo) {
		MaazounDeliverInfoDTO eMaazounDeliverInfoDTO = new MaazounDeliverInfoDTO();
		eMaazounDeliverInfoDTO.setCreateAt(String.valueOf(maazounBookDeliverInfo.getCreatedAt()));
		eMaazounDeliverInfoDTO.setCreateBy(maazounBookDeliverInfo.getCreatedBy());
		eMaazounDeliverInfoDTO.setId(String.valueOf(maazounBookDeliverInfo.getId()));
		eMaazounDeliverInfoDTO.setLocationId(String.valueOf(maazounBookDeliverInfo.getSectorFk().getLocationFk().getId()));
		eMaazounDeliverInfoDTO.setPosId(String.valueOf(maazounBookDeliverInfo.getPosFk().getId()));
		eMaazounDeliverInfoDTO.setImageUrl(maazounBookDeliverInfo.getImageUrl());
		eMaazounDeliverInfoDTO.setStatus(maazounBookDeliverInfo.getStatusFk());
		eMaazounDeliverInfoDTO.setUpdatedAt(String.valueOf(maazounBookDeliverInfo.getUpdatedAt()));
		eMaazounDeliverInfoDTO.setUpdatedBy(maazounBookDeliverInfo.getUpdatedBy());
		eMaazounDeliverInfoDTO.setRefRequestNumber(maazounBookDeliverInfo.getRefRequestNumber());
		eMaazounDeliverInfoDTO.setReceiptUrl(maazounBookDeliverInfo.getReceiptUrl());
		return eMaazounDeliverInfoDTO;
	}
	

}
