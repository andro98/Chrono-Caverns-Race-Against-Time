package com.aman.payment.auth.mapper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.aman.payment.auth.model.MaazouniaChurch;
import com.aman.payment.auth.model.dto.MaazouniaChurchDTO;

@Service
public class MaazouniaChurchMapper {
	/*
	 * MaazouniaChurch to MaazouniaChurchDTO
	 * MaazouniaChurch to MaazouniaChurchDTOs
	 * ****************************************************************************************************
	 */
	public MaazouniaChurchDTO maazouniaChurchToMaazouniaChurchDTO(MaazouniaChurch maazouniaChurch) {
        return createMaazouniaChurchDTO(maazouniaChurch);
    }
	
	public List<MaazouniaChurchDTO> maazouniaChurchsToMaazouniaChurchDTOs(List<MaazouniaChurch> maazouniaChurchs) {
		return maazouniaChurchs.stream().filter(Objects::nonNull)
				.map(this::maazouniaChurchToMaazouniaChurchDTO).collect(Collectors.toList());
	}
	
	private MaazouniaChurchDTO createMaazouniaChurchDTO(MaazouniaChurch maazouniaChurch) {
		MaazouniaChurchDTO maazouniaChurchDTO = new MaazouniaChurchDTO();
		maazouniaChurchDTO.setName(maazouniaChurch.getName());
		maazouniaChurchDTO.setId(String.valueOf(maazouniaChurch.getId()));
		maazouniaChurchDTO.setStatus(maazouniaChurch.getStatusFk());
		maazouniaChurchDTO.setCreatetionDate(String.valueOf(maazouniaChurch.getMaazouniaCreationDate()));
		maazouniaChurchDTO.setType(maazouniaChurch.getMaazouniaType());
		maazouniaChurchDTO.setCreationAttacmentUrl(maazouniaChurch.getCreationAtt());
		if(maazouniaChurch.getSectorFk() !=null) {
			maazouniaChurchDTO.setSectorId(String.valueOf(maazouniaChurch.getSectorFk().getId()));
			maazouniaChurchDTO.setSectorName(maazouniaChurch.getSectorFk().getName());	
		}
		
		return maazouniaChurchDTO;
	}
	

}
