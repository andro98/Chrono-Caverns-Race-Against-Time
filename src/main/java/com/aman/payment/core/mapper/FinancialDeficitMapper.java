package com.aman.payment.core.mapper;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.aman.payment.core.model.FinancialDeficit;
import com.aman.payment.core.model.dto.FinancialDeficitDTO;
import com.aman.payment.core.util.UtilCore;

@Service
public class FinancialDeficitMapper {
	
	/*
	 * financialDeficit to financialDeficitDTO
	 * financialDeficit to financialDeficitDTOs
	 * ****************************************************************************************************
	 */
	public FinancialDeficitDTO financialDeficitToFinancialDeficitDTO(FinancialDeficit financialDeficit) {
		return createFinancialDeficitDTO(financialDeficit);
	}

	public List<FinancialDeficitDTO> financialDeficitsToFinancialDeficitDTOs(List<FinancialDeficit> financialDeficits) {
		return financialDeficits !=null?financialDeficits.stream()
      .filter(Objects::nonNull)
				.map(this::financialDeficitToFinancialDeficitDTO).collect(Collectors.toList()):null;
	}
	
	public Set<FinancialDeficitDTO> financialDeficitsToFinancialDeficitDTOs(Set<FinancialDeficit> financialDeficits) {
		return financialDeficits.stream()
      .filter(Objects::nonNull)
				.map(this::financialDeficitToFinancialDeficitDTO).collect(Collectors.toSet());
	}
	
	private FinancialDeficitDTO createFinancialDeficitDTO(FinancialDeficit financialDeficit) {
		FinancialDeficitDTO financialDeficitDTO = new FinancialDeficitDTO();
		
	//	financialDeficitDTO.setApprovedAt(UtilCore.changeDateFormat(financialDeficit.getApprovedAt(), "dd/MM/yyyy"));
		//financialDeficitDTO.setApprovedBy(financialDeficit.getApprovedBy());
		financialDeficitDTO.setApprovedComment(financialDeficit.getApprovedComment());
		financialDeficitDTO.setAttUrl(financialDeficit.getAttUrl());
		financialDeficitDTO.setComment(financialDeficit.getComment());
		financialDeficitDTO.setCreatedAt(UtilCore.changeDateFormat(financialDeficit.getCreatedAt(), "dd/MM/yyyy"));
		financialDeficitDTO.setDeficitAmount(String.valueOf(financialDeficit.getDeficitAmount()));
		financialDeficitDTO.setFinancialDeficitId(String.valueOf(financialDeficit.getId()));
		financialDeficitDTO.setLocationCode(financialDeficit.getPullAccountFk().getLocationCode());
		financialDeficitDTO.setLocationName(financialDeficit.getPullAccountFk().getLocationName());
		financialDeficitDTO.setPosId(String.valueOf(financialDeficit.getPullAccountFk().getPosId()));
		financialDeficitDTO.setPullAccountId(String.valueOf(financialDeficit.getPullAccountFk().getId()));
		financialDeficitDTO.setPullAccountStatus(financialDeficit.getPullAccountFk().getStatusFk());
		financialDeficitDTO.setSettlementCode(financialDeficit.getPullAccountFk().getSettlementCode());
		financialDeficitDTO.setStatus(financialDeficit.getStatusFk());
		financialDeficitDTO.setUsername(financialDeficit.getCreatedBy());
		financialDeficitDTO.setServiceId(String.valueOf(financialDeficit.getServiceId()));

		
		return financialDeficitDTO;
	}
	

}
