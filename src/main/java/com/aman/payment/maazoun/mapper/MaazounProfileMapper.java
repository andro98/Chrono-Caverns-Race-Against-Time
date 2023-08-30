package com.aman.payment.maazoun.mapper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.aman.payment.auth.model.MaazounMaazouniaChurch;
import com.aman.payment.auth.model.Sector;
import com.aman.payment.auth.model.dto.JwtAuthSectorDTO;
import com.aman.payment.auth.model.dto.MaazounMaazouniaChurchDTO;
import com.aman.payment.maazoun.model.MaazounProfile;
import com.aman.payment.maazoun.model.dto.MaazounProfileDTO;

@Service
public class MaazounProfileMapper {

	/*
	 * maazoun to maazounDTO
	 * maazoun to maazounDTOs
	 * ****************************************************************************************************
	 */
	public MaazounProfileDTO maazounProfileToMaazounProfileDTO(MaazounProfile maazounProfile) {
		if (maazounProfile!=null) {
	        return createMaazounProfileDTO(maazounProfile);

		}
		else return null ;
    }
	
	public List<MaazounProfileDTO> maazounProfilesToMaazounProfileDTOs(List<MaazounProfile> maazounProfiles) {
		return maazounProfiles.stream().filter(Objects::nonNull)
				.map(this::maazounProfileToMaazounProfileDTO).collect(Collectors.toList());
	}
	
	private MaazounProfileDTO createMaazounProfileDTO(MaazounProfile maazounProfile) {
		MaazounProfileDTO maazounProfileDTO = new MaazounProfileDTO();
		maazounProfileDTO.setName(maazounProfile.getName());
		maazounProfileDTO.setId(String.valueOf(maazounProfile.getId()));
		maazounProfileDTO.setNationalId(maazounProfile.getNationalId());
		maazounProfileDTO.setImageUrl(maazounProfile.getImageUrl());
		maazounProfileDTO.setMobile(maazounProfile.getMobile());
		maazounProfileDTO.setAddress(maazounProfile.getAddress());
		maazounProfileDTO.setActive(String.valueOf(maazounProfile.getActive()));
		maazounProfileDTO.setCardNumber(maazounProfile.getCardNumber());
		maazounProfileDTO.setImageUrl(maazounProfile.getImageUrl());
		maazounProfileDTO.setMaazounType(maazounProfile.getMaazounType());
		maazounProfileDTO.setBookMoragaaQuota(maazounProfile.getBookMoragaaQuota());
		maazounProfileDTO.setBookTalakQuota(maazounProfile.getBookTalakQuota());
		maazounProfileDTO.setBookTasadokQuota(maazounProfile.getBookTasadokQuota());
		maazounProfileDTO.setBookZawagMellyQuota(maazounProfile.getBookZawagMellyQuota());
		maazounProfileDTO.setBookZawagMuslimQuota(maazounProfile.getBookZawagMuslimQuota());
		maazounProfileDTO.setCustody(String.valueOf(maazounProfile.getIsCustody()));
		maazounProfileDTO.setCloseCustody(String.valueOf(maazounProfile.getCloseCustody()));
		maazounProfileDTO.setSuspendedReason(maazounProfile.getSuspendedReason());
		
		maazounProfileDTO.setBookMoragaaQuotaContractCount(maazounProfile.getBookMoragaaQuotaContractCount());
		maazounProfileDTO.setBookTalakQuotaContractCount(maazounProfile.getBookTalakQuotaContractCount());
		maazounProfileDTO.setBookTasadokQuotaContractCount(maazounProfile.getBookTasadokQuotaContractCount());
		maazounProfileDTO.setBookZawagMellyQuotaContractCount(maazounProfile.getBookZawagMellyQuotaContractCount());
		maazounProfileDTO.setBookZawagMuslimQuotaContractCount(maazounProfile.getBookZawagMuslimQuotaContractCount());
		maazounProfileDTO.setHasExeption(String.valueOf(maazounProfile.getHasExeption()));
	
		
		for(Sector sector : maazounProfile.getSectors()) {
			JwtAuthSectorDTO dto = new JwtAuthSectorDTO();
			dto.setId(String.valueOf(sector.getId()));
			dto.setName(sector.getName());
			maazounProfileDTO.getSectors().add(dto);
		}
		for(MaazounMaazouniaChurch eMaazounMaazouniaChurch : maazounProfile.getMaazounMaazouniaChurch()) {
//			MaazouniaChurchDTO maazouniaChurchDto = new MaazouniaChurchDTO();
//			maazouniaChurchDto.setId(String.valueOf(eMaazounMaazouniaChurch.getMaazouniaChurchFk().getId()));
//			maazouniaChurchDto.setName(eMaazounMaazouniaChurch.getMaazouniaChurchFk().getName());
//			maazounProfileDTO.getMaazouniaChurchs().add(maazouniaChurchDto);
//			
			
			MaazounMaazouniaChurchDTO eMaazounMaazouniaChurchDTO = new MaazounMaazouniaChurchDTO();
			eMaazounMaazouniaChurchDTO.setMaazouniaChurchId(String.valueOf(eMaazounMaazouniaChurch.getMaazouniaChurchFk().getId()));
			eMaazounMaazouniaChurchDTO.setMaazouniaChurchName(eMaazounMaazouniaChurch.getMaazouniaChurchFk().getName());
			eMaazounMaazouniaChurchDTO.setMaazouniaType(eMaazounMaazouniaChurch.getMaazouniaType());
			eMaazounMaazouniaChurchDTO.setMaazounId(String.valueOf(eMaazounMaazouniaChurch.getMaazounFk().getId()));
			eMaazounMaazouniaChurchDTO.setId(String.valueOf(eMaazounMaazouniaChurch.getId()));

			maazounProfileDTO.getMaazouniaChurchs().add(eMaazounMaazouniaChurchDTO);
		}
		
		
		return maazounProfileDTO;
	}
	

}
