package com.aman.payment.maazoun.mapper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.aman.payment.maazoun.model.MaazounBookRequestInfo;
import com.aman.payment.maazoun.model.MaazounBookWarehouse;
import com.aman.payment.maazoun.model.dto.MaazounRequestInfoDTO;

@Service
public class MaazounRequestInfoMapper {

	/*
	 * MaazounRequestInfo to MaazounRequestInfoDTO
	 * MaazounRequestInfos to MaazounRequestInfoDTOs
	 * ****************************************************************************************************
	 */
	public MaazounRequestInfoDTO maazounRequestInfoToMaazounRequestInfoDTO(MaazounBookRequestInfo maazounRequestInfo) {
		if (maazounRequestInfo!=null) {
	        return createMaazounRequestInfoDTO(maazounRequestInfo);

		}
		else return null ;
    }
	
	public List<MaazounRequestInfoDTO> maazounBookSupplyOrdersToMaazounBookSupplyOrderDTOs(List<MaazounBookRequestInfo> maazounRequestInfoSet) {
		return maazounRequestInfoSet.stream().filter(Objects::nonNull)
				.map(this::maazounRequestInfoToMaazounRequestInfoDTO).collect(Collectors.toList());
	}
	
	private MaazounRequestInfoDTO createMaazounRequestInfoDTO(MaazounBookRequestInfo maazounRequestInfo) {
		MaazounRequestInfoDTO maazounRequestInfoDTO = new MaazounRequestInfoDTO();
		maazounRequestInfoDTO.setCreateAt(String.valueOf(maazounRequestInfo.getCreatedAt()));
		maazounRequestInfoDTO.setCreateBy(maazounRequestInfo.getCreatedBy());
		maazounRequestInfoDTO.setId(String.valueOf(maazounRequestInfo.getId()));
		maazounRequestInfoDTO.setLocationId(String.valueOf(maazounRequestInfo.getSectorFk().getLocationFk().getId()));
		maazounRequestInfoDTO.setLocationName(maazounRequestInfo.getSectorFk().getLocationFk().getName());
		maazounRequestInfoDTO.setPosId(String.valueOf(maazounRequestInfo.getPosFk().getId()));
		maazounRequestInfoDTO.setPosName(maazounRequestInfo.getPosFk().getName());
		maazounRequestInfoDTO.setImageUrl(maazounRequestInfo.getReceiptUrl());
		maazounRequestInfoDTO.setStatus(maazounRequestInfo.getStatusFk());
		maazounRequestInfoDTO.setUpdatedAt(String.valueOf(maazounRequestInfo.getUpdatedAt()));
		maazounRequestInfoDTO.setUpdatedBy(maazounRequestInfo.getUpdatedBy());
		maazounRequestInfoDTO.setMaazounName(maazounRequestInfo.getMaazounProfileFk().getName());
		maazounRequestInfoDTO.setMaazounNationalId(maazounRequestInfo.getMaazounProfileFk().getNationalId());
		maazounRequestInfoDTO.setTransactionCode(maazounRequestInfo.getTransactionCode());
		maazounRequestInfoDTO.setMaazounCardNumber(maazounRequestInfo.getMaazounProfileFk().getCardNumber());
		maazounRequestInfoDTO.setAmount(String.valueOf(maazounRequestInfo.getAmount()));
		//maazounRequestInfoDTO.setBookSerialNumber(maazounRequestInfo.getBookSerialNumber());
		maazounRequestInfoDTO.setRefRequestNumber(maazounRequestInfo.getRefRequestNumber());
		maazounRequestInfoDTO.setBookTypeName(maazounRequestInfo.getBookTypeName());
		maazounRequestInfoDTO.setPaymentCode(maazounRequestInfo.getPaymentCode());
		maazounRequestInfoDTO.setMaazouniaName(maazounRequestInfo.getMaazouniaName());

		MaazounBookWarehouse maazounBookWarehouseObj = maazounRequestInfo.getMaazounBookWarehouseSet().stream()
				.filter(x -> x.getSerialNumber() != null
						? x.getSerialNumber().equals(maazounRequestInfo.getBookSerialNumber())
						: x.getBookFinancialNumber().equals(maazounRequestInfo.getBookSerialNumber()))
				.findAny().orElse(null);
		
		if (maazounBookWarehouseObj != null) {
			maazounRequestInfoDTO.setCurrentBookStatus(maazounBookWarehouseObj.getStatusFk());
			maazounRequestInfoDTO.setContractCount(String.valueOf(maazounBookWarehouseObj.getContractCount()));
			maazounRequestInfoDTO.setBookSerialNumber(maazounBookWarehouseObj.getSerialNumber());
			maazounRequestInfoDTO.setBookFinancialNumber(maazounBookWarehouseObj.getBookFinancialNumber());

		}
		
		return maazounRequestInfoDTO;
	}
	

}
