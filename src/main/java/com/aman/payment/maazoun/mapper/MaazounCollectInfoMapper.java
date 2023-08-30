package com.aman.payment.maazoun.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.aman.payment.auth.model.SubService;
import com.aman.payment.maazoun.model.MaazounBookCollectionInfo;
import com.aman.payment.maazoun.model.MaazounBookValidation;
import com.aman.payment.maazoun.model.MaazounBookWarehouse;
import com.aman.payment.maazoun.model.dto.ContractValidationDTO;
import com.aman.payment.maazoun.model.dto.MaazounCollectInfoDTO;
import com.aman.payment.util.Util;

@Service
public class MaazounCollectInfoMapper {

	/*
	 * MaazounCollectionInfo to MaazounCollectionInfoDTO MaazounCollectionInfos to
	 * MaazounCollectionInfoDTOs
	 * *****************************************************************************
	 * ***********************
	 */
	
	public MaazounCollectInfoDTO reviewContractDTO(MaazounBookCollectionInfo maazounBookCollectionInfo) {
		if (maazounBookCollectionInfo != null) {
			return createReviewContractDTO(maazounBookCollectionInfo);

		} else
			return null;
	}

	public MaazounCollectInfoDTO previewAddCollectionInfoDTO(MaazounBookCollectionInfo maazounBookCollectionInfo) {
		if (maazounBookCollectionInfo != null) {
			return createPreviewAddCollectionInfoDTO(maazounBookCollectionInfo);

		} else
			return null;
	}
	
	public MaazounCollectInfoDTO previewPayCollectionInfoDTO(MaazounBookCollectionInfo maazounBookCollectionInfo,
			MaazounCollectInfoDTO maazounCollectInfoQuota) {
		if (maazounBookCollectionInfo != null) {
			return createPreviewPayCollectionInfoDTO(maazounBookCollectionInfo,
					maazounCollectInfoQuota);

		} else
			return null;
	}
	
	public List<String> previewAddCollectionInfoDTOs(List<MaazounBookCollectionInfo> maazounBookCollectionInfoSet) {
		List<String> results = new ArrayList<String>();
		maazounBookCollectionInfoSet.stream().forEach(s -> {
			results.add(createPreviewAddCollectionInfoDTO(s).toString());
		});
		return results;
	}

	private MaazounCollectInfoDTO createReviewContractDTO(MaazounBookCollectionInfo eMaazounBookCollectionInfo) {

		MaazounCollectInfoDTO eMaazounCollectInfoDTO = new MaazounCollectInfoDTO();
		eMaazounCollectInfoDTO.setCreateAt(Util.dateFormat(eMaazounBookCollectionInfo.getCreatedAt()));
		eMaazounCollectInfoDTO.setCreateBy(eMaazounBookCollectionInfo.getCreatedBy());
		eMaazounCollectInfoDTO.setId(String.valueOf(eMaazounBookCollectionInfo.getId()));
		eMaazounCollectInfoDTO.setImageUrl(eMaazounBookCollectionInfo.getImageUrl());
		eMaazounCollectInfoDTO.setStatus(eMaazounBookCollectionInfo.getStatusFk());
		eMaazounCollectInfoDTO.setMaazounName(eMaazounBookCollectionInfo.getMaazounProfileFk().getName());
		eMaazounCollectInfoDTO.setContractType(eMaazounBookCollectionInfo.getContractTypeName());

		if (eMaazounBookCollectionInfo.getContractNumber().contains("-")) {
			eMaazounCollectInfoDTO.setCustody(String.valueOf(true));
		} else {
			eMaazounCollectInfoDTO.setCustody(String.valueOf(false));
		}
		eMaazounCollectInfoDTO.setContractNumber(eMaazounBookCollectionInfo.getContractNumber());

		eMaazounCollectInfoDTO.setContractDate(Util.dateFormatReport(eMaazounBookCollectionInfo.getContractDate()));
		eMaazounCollectInfoDTO
				.setContractPaidAmount(String.valueOf(eMaazounBookCollectionInfo.getContractPaidAmount()));
		eMaazounCollectInfoDTO.setFeeAmount(String.valueOf(eMaazounBookCollectionInfo.getFeeAmount()));
		eMaazounCollectInfoDTO.setHusbandName(eMaazounBookCollectionInfo.getHusbandName());
		eMaazounCollectInfoDTO.setLocationName(eMaazounBookCollectionInfo.getSectorFk().getLocationFk().getName());
		eMaazounCollectInfoDTO.setPosName(eMaazounBookCollectionInfo.getPosFk().getName());
		if (eMaazounBookCollectionInfo.getSectorFk() != null)
			eMaazounCollectInfoDTO.setSectorName(eMaazounBookCollectionInfo.getSectorFk().getName());
		eMaazounCollectInfoDTO.setWifeName(eMaazounBookCollectionInfo.getWifeName());
		eMaazounCollectInfoDTO.setHusbandNationalId(eMaazounBookCollectionInfo.getHusbandNationalId());
		eMaazounCollectInfoDTO.setWifeNationalId(eMaazounBookCollectionInfo.getWifeNationalId());

		List<String> validationList = new ArrayList<String>();
		for (MaazounBookValidation obj : eMaazounBookCollectionInfo.getMaazounBookValidationSet()) {
			ContractValidationDTO eContractValidationDTO = new ContractValidationDTO();
			eContractValidationDTO.setKey(obj.getMaazounCheckListLookupFk().getName());
			eContractValidationDTO.setValue(String.valueOf(obj.isValueDesc()));
			validationList.add(eContractValidationDTO.toString());
		}
		eMaazounCollectInfoDTO.setValidationSet(validationList);
		eMaazounCollectInfoDTO.setPosId(String.valueOf(eMaazounBookCollectionInfo.getPosFk().getId()));

		return eMaazounCollectInfoDTO;
	}

	public MaazounCollectInfoDTO createPreviewAddCollectionInfoDTO(
			MaazounBookCollectionInfo eMaazounBookCollectionInfo) {

		MaazounCollectInfoDTO eMaazounCollectInfoDTO = new MaazounCollectInfoDTO();
		eMaazounCollectInfoDTO.setCreateAt(Util.dateFormat(eMaazounBookCollectionInfo.getCreatedAt()));
		eMaazounCollectInfoDTO.setCreateBy(eMaazounBookCollectionInfo.getCreatedBy());
		if (eMaazounBookCollectionInfo.getSectorFk() != null)
			eMaazounCollectInfoDTO.setSectorName(eMaazounBookCollectionInfo.getSectorFk().getName());
		eMaazounCollectInfoDTO.setHusbandNationalId(eMaazounBookCollectionInfo.getHusbandNationalId());
		eMaazounCollectInfoDTO.setWifeNationalId(eMaazounBookCollectionInfo.getWifeNationalId());
		eMaazounCollectInfoDTO.setId(String.valueOf(eMaazounBookCollectionInfo.getId()));
		eMaazounCollectInfoDTO.setMaazounName(eMaazounBookCollectionInfo.getMaazounProfileFk().getName());
		eMaazounCollectInfoDTO.setBookSerialNumber(eMaazounBookCollectionInfo.getBookSerialNumber());
		eMaazounCollectInfoDTO.setContractNumber(eMaazounBookCollectionInfo.getContractNumber());
		eMaazounCollectInfoDTO.setContractPaidAmount(String.valueOf(eMaazounBookCollectionInfo.getContractPaidAmount()));
 		eMaazounCollectInfoDTO.setContractDate(Util.dateFormatReport(eMaazounBookCollectionInfo.getContractDate()));
		eMaazounCollectInfoDTO.setFeeAmount(String.valueOf(eMaazounBookCollectionInfo.getFeeAmount()));
		eMaazounCollectInfoDTO.setHusbandName(eMaazounBookCollectionInfo.getHusbandName());
		eMaazounCollectInfoDTO.setWifeName(eMaazounBookCollectionInfo.getWifeName());
		eMaazounCollectInfoDTO.setContractType(eMaazounBookCollectionInfo.getContractTypeName());
		eMaazounCollectInfoDTO.setStatus(eMaazounBookCollectionInfo.getStatusFk());
		eMaazounCollectInfoDTO.setPosId(String.valueOf(eMaazounBookCollectionInfo.getPosFk().getId()));
		eMaazounCollectInfoDTO.setImageUrl(eMaazounBookCollectionInfo.getImageUrl());

		return eMaazounCollectInfoDTO;
	}

	
	public MaazounCollectInfoDTO createPreviewPayCollectionInfoDTO(
			MaazounBookCollectionInfo eMaazounBookCollectionInfo, MaazounCollectInfoDTO maazounCollectInfoQuota) {

		MaazounCollectInfoDTO eMaazounCollectInfoDTO = new MaazounCollectInfoDTO();
		eMaazounCollectInfoDTO.setId(String.valueOf(eMaazounBookCollectionInfo.getId()));
		eMaazounCollectInfoDTO.setMaazounName(eMaazounBookCollectionInfo.getMaazounProfileFk().getName());
		eMaazounCollectInfoDTO.setBookSerialNumber(eMaazounBookCollectionInfo.getBookSerialNumber());
		eMaazounCollectInfoDTO.setContractNumber(eMaazounBookCollectionInfo.getContractNumber());

		eMaazounCollectInfoDTO.setContractDate(Util.dateFormatReport(eMaazounBookCollectionInfo.getContractDate()));
		eMaazounCollectInfoDTO.setFeeAmount(String.valueOf(eMaazounBookCollectionInfo.getFeeAmount()));
		eMaazounCollectInfoDTO.setContractPaidAmount(String.valueOf(eMaazounBookCollectionInfo.getContractPaidAmount()));
		eMaazounCollectInfoDTO.setHusbandName(eMaazounBookCollectionInfo.getHusbandName());
		eMaazounCollectInfoDTO.setWifeName(eMaazounBookCollectionInfo.getWifeName());
		eMaazounCollectInfoDTO.setContractType(eMaazounBookCollectionInfo.getContractTypeName());
		eMaazounCollectInfoDTO.setTypeId(String.valueOf(eMaazounBookCollectionInfo.getContractTypeId()));
		eMaazounCollectInfoDTO.setStatus(eMaazounBookCollectionInfo.getStatusFk());
		eMaazounCollectInfoDTO.setSectorId(String.valueOf(eMaazounBookCollectionInfo.getSectorFk().getId()));
		eMaazounCollectInfoDTO.setLocationName(eMaazounBookCollectionInfo.getSectorFk().getLocationFk().getName());
		eMaazounCollectInfoDTO.setSectorName(eMaazounBookCollectionInfo.getSectorFk().getName());

		if (maazounCollectInfoQuota != null) {
			eMaazounCollectInfoDTO.setQuotaContractAbnytMhakm(maazounCollectInfoQuota.getQuotaContractAbnytMhakm());
			eMaazounCollectInfoDTO
					.setQuotaContractGamiyaMazouneen(maazounCollectInfoQuota.getQuotaContractGamiyaMazouneen());
			eMaazounCollectInfoDTO.setQuotaContractIdafy(maazounCollectInfoQuota.getQuotaContractIdafy());
			eMaazounCollectInfoDTO.setQuotaContractMokrr(maazounCollectInfoQuota.getQuotaContractMokrr());
			eMaazounCollectInfoDTO.setQuotaContractTameenOsra(maazounCollectInfoQuota.getQuotaContractTameenOsra());
			eMaazounCollectInfoDTO.setQuotaContractPercentFirstThousand(
					maazounCollectInfoQuota.getQuotaContractPercentFirstThousand());
			eMaazounCollectInfoDTO.setQuotaContractPercentRemainingAmount(
					maazounCollectInfoQuota.getQuotaContractPercentRemainingAmount());
		}
		
		return eMaazounCollectInfoDTO;
	}
	
	
	
	
	
	
	
	
	public MaazounCollectInfoDTO maazounCollectionInfoToMaazounCollectionInfoDTO(String contractPaidAmount,
			MaazounBookCollectionInfo maazounBookCollectionInfo, SubService subService,
			MaazounCollectInfoDTO maazounCollectInfoQuota, String maazouniaChurchNameType) {
		if (maazounBookCollectionInfo != null) {
			return createMaazounCollectionInfoDTO(contractPaidAmount, maazounBookCollectionInfo, subService,
					maazounCollectInfoQuota, maazouniaChurchNameType);

		} else
			return null;
	}

	public MaazounCollectInfoDTO maazounLightAdvancedCollectionInfoToMaazounCollectionInfoDTO(
			MaazounBookCollectionInfo maazounBookCollectionInfo) {
		if (maazounBookCollectionInfo != null) {
			return createLightAdvancedSearchMaazounCollectionInfoDTO(maazounBookCollectionInfo);

		} else
			return null;
	}

	public MaazounCollectInfoDTO maazounCollectionInfoToMaazounCollectionInfoDTO(
			MaazounBookCollectionInfo maazounBookCollectionInfo) {
		if (maazounBookCollectionInfo != null) {
			return createMaazounCollectionInfoDTO(maazounBookCollectionInfo);

		} else
			return null;
	}

	public MaazounCollectInfoDTO maazounCollectionInfoToLightMaazounCollectionInfoDTO(
			MaazounBookCollectionInfo maazounBookCollectionInfo) {
		if (maazounBookCollectionInfo != null) {
			return createLightMaazounCollectionInfoDTO(maazounBookCollectionInfo);

		} else
			return null;

	}

	public List<MaazounCollectInfoDTO> maazounCollectionInfosToMaazounCollectionInfoDTOs(
			List<MaazounBookCollectionInfo> maazounBookCollectionInfoSet) {
		List<MaazounCollectInfoDTO> dtoList = new ArrayList<MaazounCollectInfoDTO>();
		maazounBookCollectionInfoSet.stream().forEach(s -> {
			dtoList.add(createMaazounCollectionInfoDTO(s));
		});
		return dtoList;

	}

	private MaazounCollectInfoDTO createMaazounCollectionInfoDTO(MaazounBookCollectionInfo eMaazounBookCollectionInfo) {

		MaazounCollectInfoDTO eMaazounCollectInfoDTO = new MaazounCollectInfoDTO();
		eMaazounCollectInfoDTO.setCreateAt(String.valueOf(eMaazounBookCollectionInfo.getCreatedAt()));
		eMaazounCollectInfoDTO.setCreateBy(eMaazounBookCollectionInfo.getCreatedBy());
		eMaazounCollectInfoDTO.setId(String.valueOf(eMaazounBookCollectionInfo.getId()));
		eMaazounCollectInfoDTO.setImageUrl(eMaazounBookCollectionInfo.getImageUrl());
		eMaazounCollectInfoDTO.setStatus(eMaazounBookCollectionInfo.getStatusFk());
		eMaazounCollectInfoDTO.setMaazounName(eMaazounBookCollectionInfo.getMaazounProfileFk().getName());
		eMaazounCollectInfoDTO.setTransactionCode(eMaazounBookCollectionInfo.getTransactionCode());
		eMaazounCollectInfoDTO.setBookSerialNumber(eMaazounBookCollectionInfo.getBookSerialNumber());
		eMaazounCollectInfoDTO.setContractType(eMaazounBookCollectionInfo.getContractTypeName());
		MaazounBookWarehouse maazounBookWarehouseObj = eMaazounBookCollectionInfo.getMaazounBookWarehouseSet().stream()
				.filter(x -> x.getContractNumber() != null
						? x.getContractNumber().equals(eMaazounBookCollectionInfo.getContractNumber())
						: x.getContractFinancialNumber().equals(eMaazounBookCollectionInfo.getContractNumber()))
				.findAny().orElse(null);
		if (maazounBookWarehouseObj != null) {
			eMaazounCollectInfoDTO.setBookFinancialNumber(maazounBookWarehouseObj.getBookFinancialNumber());
			eMaazounCollectInfoDTO
					.setCustody(String.valueOf(maazounBookWarehouseObj.getMaazounBookSupplyOrderFk().getIsCustody()));
			eMaazounCollectInfoDTO.setCurrentBookStatus(maazounBookWarehouseObj.getStatusFk());
			eMaazounCollectInfoDTO.setContractCount(String.valueOf(maazounBookWarehouseObj.getContractCount()));
			eMaazounCollectInfoDTO.setBookSerialNumber(maazounBookWarehouseObj.getSerialNumber());
			eMaazounCollectInfoDTO.setBookFinancialNumber(maazounBookWarehouseObj.getBookFinancialNumber());
			eMaazounCollectInfoDTO.setMaazouniaChurchNameType(maazounBookWarehouseObj.getMaazounBookRequestInfoFk().getMaazouniaName());

		}

		eMaazounCollectInfoDTO.setTypeId(String.valueOf(eMaazounBookCollectionInfo.getContractTypeId()));

		eMaazounCollectInfoDTO.setRefRequestNumber(eMaazounBookCollectionInfo.getRefRequestNumber());
		eMaazounCollectInfoDTO.setContractNumber(eMaazounBookCollectionInfo.getContractNumber());
		eMaazounCollectInfoDTO.setId(String.valueOf(eMaazounBookCollectionInfo.getId()));
		eMaazounCollectInfoDTO.setReceiptUrl(eMaazounBookCollectionInfo.getReceiptUrl());

		eMaazounCollectInfoDTO.setContractDate(Util.dateFormatReport(eMaazounBookCollectionInfo.getContractDate()));
		eMaazounCollectInfoDTO
				.setContractPaidAmount(String.valueOf(eMaazounBookCollectionInfo.getContractPaidAmount()));
		eMaazounCollectInfoDTO.setFeeAmount(String.valueOf(eMaazounBookCollectionInfo.getFeeAmount()));
		eMaazounCollectInfoDTO.setHusbandName(eMaazounBookCollectionInfo.getHusbandName());
		eMaazounCollectInfoDTO.setLocationName(eMaazounBookCollectionInfo.getSectorFk().getLocationFk().getName());
		eMaazounCollectInfoDTO.setPosName(eMaazounBookCollectionInfo.getPosFk().getName());
		eMaazounCollectInfoDTO.setPosId(String.valueOf(eMaazounBookCollectionInfo.getPosFk().getId()));
		eMaazounCollectInfoDTO.setTransactionCode(eMaazounBookCollectionInfo.getTransactionCode());
		if (eMaazounBookCollectionInfo.getSectorFk() != null)
			eMaazounCollectInfoDTO.setSectorName(eMaazounBookCollectionInfo.getSectorFk().getName());
		eMaazounCollectInfoDTO.setWifeName(eMaazounBookCollectionInfo.getWifeName());

		List<String> validationList = new ArrayList<String>();
		for (MaazounBookValidation obj : eMaazounBookCollectionInfo.getMaazounBookValidationSet()) {
			ContractValidationDTO eContractValidationDTO = new ContractValidationDTO();
			eContractValidationDTO.setKey(obj.getMaazounCheckListLookupFk().getName());
			eContractValidationDTO.setValue(String.valueOf(obj.isValueDesc()));
			validationList.add(eContractValidationDTO.toString());
		}
		eMaazounCollectInfoDTO.setValidationSet(validationList);
		eMaazounCollectInfoDTO.setBookTypeName(eMaazounBookCollectionInfo.getContractTypeName());
		eMaazounCollectInfoDTO.setPaymentCode(eMaazounBookCollectionInfo.getPaymentCode());

		return eMaazounCollectInfoDTO;
	}

	private MaazounCollectInfoDTO createMaazounCollectionInfoDTO(String contractPaidAmount,
			MaazounBookCollectionInfo eMaazounBookCollectionInfo, SubService subService,
			MaazounCollectInfoDTO maazounCollectInfoQuota, String maazouniaChurchNameType) {

		MaazounCollectInfoDTO eMaazounCollectInfoDTO = new MaazounCollectInfoDTO();
		eMaazounCollectInfoDTO.setCreateAt(String.valueOf(eMaazounBookCollectionInfo.getCreatedAt()));
		eMaazounCollectInfoDTO.setCreateBy(eMaazounBookCollectionInfo.getCreatedBy());
		eMaazounCollectInfoDTO.setId(String.valueOf(eMaazounBookCollectionInfo.getId()));
		eMaazounCollectInfoDTO.setImageUrl(eMaazounBookCollectionInfo.getImageUrl());
		eMaazounCollectInfoDTO.setStatus(eMaazounBookCollectionInfo.getStatusFk());
		eMaazounCollectInfoDTO.setMaazounName(eMaazounBookCollectionInfo.getMaazounProfileFk().getName());
		eMaazounCollectInfoDTO.setTransactionCode(eMaazounBookCollectionInfo.getTransactionCode());
		eMaazounCollectInfoDTO.setBookSerialNumber(eMaazounBookCollectionInfo.getBookSerialNumber());
		MaazounBookWarehouse maazounBookWarehouseObj = eMaazounBookCollectionInfo.getMaazounBookWarehouseSet().stream()
				.filter(x -> eMaazounBookCollectionInfo.getContractNumber().equals(x.getContractFinancialNumber())
						|| eMaazounBookCollectionInfo.getContractNumber().equals(x.getContractNumber()))
				.findAny().orElse(null);
		if (maazounBookWarehouseObj != null)
			eMaazounCollectInfoDTO.setBookFinancialNumber(maazounBookWarehouseObj.getBookFinancialNumber());
		eMaazounCollectInfoDTO.setRefRequestNumber(eMaazounBookCollectionInfo.getRefRequestNumber());
		eMaazounCollectInfoDTO.setContractNumber(eMaazounBookCollectionInfo.getContractNumber());
		eMaazounCollectInfoDTO.setId(String.valueOf(eMaazounBookCollectionInfo.getId()));
		eMaazounCollectInfoDTO.setReceiptUrl(eMaazounBookCollectionInfo.getReceiptUrl());

		eMaazounCollectInfoDTO.setContractDate(Util.dateFormatReport(eMaazounBookCollectionInfo.getContractDate()));
		eMaazounCollectInfoDTO
				.setContractPaidAmount(String.valueOf(eMaazounBookCollectionInfo.getContractPaidAmount()));
		eMaazounCollectInfoDTO.setFeeAmount(String.valueOf(eMaazounBookCollectionInfo.getFeeAmount()));
		eMaazounCollectInfoDTO.setHusbandName(eMaazounBookCollectionInfo.getHusbandName());
		eMaazounCollectInfoDTO.setLocationName(eMaazounBookCollectionInfo.getSectorFk().getLocationFk().getName());
		eMaazounCollectInfoDTO.setPosName(eMaazounBookCollectionInfo.getPosFk().getName());
		if (eMaazounBookCollectionInfo.getSectorFk() != null) {
			eMaazounCollectInfoDTO.setSectorName(eMaazounBookCollectionInfo.getSectorFk().getName());
			eMaazounCollectInfoDTO.setSectorId(String.valueOf(eMaazounBookCollectionInfo.getSectorFk().getId()));
		}
		eMaazounCollectInfoDTO.setWifeName(eMaazounBookCollectionInfo.getWifeName());
		eMaazounCollectInfoDTO.setTypeId(String.valueOf(subService.getId()));
		eMaazounCollectInfoDTO.setContractType(subService.getName());
		List<String> validationList = new ArrayList<String>();
		for (MaazounBookValidation obj : eMaazounBookCollectionInfo.getMaazounBookValidationSet()) {
			ContractValidationDTO eContractValidationDTO = new ContractValidationDTO();
			eContractValidationDTO.setKey(obj.getMaazounCheckListLookupFk().getName());
			eContractValidationDTO.setValue(String.valueOf(obj.isValueDesc()));
			validationList.add(eContractValidationDTO.toString());
		}
		eMaazounCollectInfoDTO.setValidationSet(validationList);

		if (maazounCollectInfoQuota != null) {
			eMaazounCollectInfoDTO.setQuotaContractAbnytMhakm(maazounCollectInfoQuota.getQuotaContractAbnytMhakm());
			eMaazounCollectInfoDTO
					.setQuotaContractGamiyaMazouneen(maazounCollectInfoQuota.getQuotaContractGamiyaMazouneen());
			eMaazounCollectInfoDTO.setQuotaContractIdafy(maazounCollectInfoQuota.getQuotaContractIdafy());
			eMaazounCollectInfoDTO.setQuotaContractMokrr(maazounCollectInfoQuota.getQuotaContractMokrr());
			eMaazounCollectInfoDTO.setQuotaContractTameenOsra(maazounCollectInfoQuota.getQuotaContractTameenOsra());
			eMaazounCollectInfoDTO.setQuotaContractPercentFirstThousand(
					maazounCollectInfoQuota.getQuotaContractPercentFirstThousand());
			eMaazounCollectInfoDTO.setQuotaContractPercentRemainingAmount(
					maazounCollectInfoQuota.getQuotaContractPercentRemainingAmount());
		}

		eMaazounCollectInfoDTO.setMaazouniaChurchNameType(maazouniaChurchNameType);

		return eMaazounCollectInfoDTO;
	}

	private MaazounCollectInfoDTO createLightMaazounCollectionInfoDTO(
			MaazounBookCollectionInfo eMaazounBookCollectionInfo) {

		MaazounCollectInfoDTO eMaazounCollectInfoDTO = new MaazounCollectInfoDTO();

		eMaazounCollectInfoDTO.setStatus(eMaazounBookCollectionInfo.getStatusFk());
		return eMaazounCollectInfoDTO;
	}

	private MaazounCollectInfoDTO createLightAdvancedSearchMaazounCollectionInfoDTO(
			MaazounBookCollectionInfo eMaazounBookCollectionInfo) {

		MaazounCollectInfoDTO eMaazounCollectInfoDTO = new MaazounCollectInfoDTO();
		eMaazounCollectInfoDTO.setCreateAt(String.valueOf(eMaazounBookCollectionInfo.getCreatedAt()));
		eMaazounCollectInfoDTO.setCreateBy(eMaazounBookCollectionInfo.getCreatedBy());
		eMaazounCollectInfoDTO.setId(String.valueOf(eMaazounBookCollectionInfo.getId()));
		eMaazounCollectInfoDTO.setImageUrl(eMaazounBookCollectionInfo.getImageUrl());
		eMaazounCollectInfoDTO.setStatus(eMaazounBookCollectionInfo.getStatusFk());
		eMaazounCollectInfoDTO.setMaazounName(eMaazounBookCollectionInfo.getMaazounProfileFk().getName());
		eMaazounCollectInfoDTO.setTransactionCode(eMaazounBookCollectionInfo.getTransactionCode());
		eMaazounCollectInfoDTO.setBookSerialNumber(eMaazounBookCollectionInfo.getBookSerialNumber());
		MaazounBookWarehouse maazounBookWarehouseObj = eMaazounBookCollectionInfo.getMaazounBookWarehouseSet().stream()
				.filter(x -> x.getContractNumber() != null
						? x.getContractNumber().equals(eMaazounBookCollectionInfo.getContractNumber())
						: x.getContractFinancialNumber().equals(eMaazounBookCollectionInfo.getContractNumber()))
				.findAny().orElse(null);
		if (maazounBookWarehouseObj != null)
			eMaazounCollectInfoDTO.setBookFinancialNumber(maazounBookWarehouseObj.getBookFinancialNumber());

		eMaazounCollectInfoDTO.setRefRequestNumber(eMaazounBookCollectionInfo.getRefRequestNumber());
		eMaazounCollectInfoDTO.setContractNumber(eMaazounBookCollectionInfo.getContractNumber());
		eMaazounCollectInfoDTO.setId(String.valueOf(eMaazounBookCollectionInfo.getId()));
		eMaazounCollectInfoDTO.setReceiptUrl(eMaazounBookCollectionInfo.getReceiptUrl());
		eMaazounCollectInfoDTO.setContractType(eMaazounBookCollectionInfo.getContractTypeName());
		eMaazounCollectInfoDTO.setPaymentCode(eMaazounBookCollectionInfo.getPaymentCode());

		eMaazounCollectInfoDTO.setContractDate(Util.dateFormatReport(eMaazounBookCollectionInfo.getContractDate()));
		eMaazounCollectInfoDTO
				.setContractPaidAmount(String.valueOf(eMaazounBookCollectionInfo.getContractPaidAmount()));
		eMaazounCollectInfoDTO.setFeeAmount(String.valueOf(eMaazounBookCollectionInfo.getFeeAmount()));
		eMaazounCollectInfoDTO.setHusbandName(eMaazounBookCollectionInfo.getHusbandName());
		eMaazounCollectInfoDTO.setLocationName(eMaazounBookCollectionInfo.getSectorFk().getLocationFk().getName());
		eMaazounCollectInfoDTO.setPosName(eMaazounBookCollectionInfo.getPosFk().getName());
		eMaazounCollectInfoDTO.setPaymentCode(eMaazounBookCollectionInfo.getPaymentCode());

		if (eMaazounBookCollectionInfo.getSectorFk() != null)
			eMaazounCollectInfoDTO.setSectorName(eMaazounBookCollectionInfo.getSectorFk().getName());
		eMaazounCollectInfoDTO.setWifeName(eMaazounBookCollectionInfo.getWifeName());
		eMaazounCollectInfoDTO.setHusbandNationalId(eMaazounBookCollectionInfo.getHusbandNationalId());
		eMaazounCollectInfoDTO.setWifeNationalId(eMaazounBookCollectionInfo.getWifeNationalId());
		List<String> validationList = new ArrayList<String>();
		for (MaazounBookValidation obj : eMaazounBookCollectionInfo.getMaazounBookValidationSet()) {
			ContractValidationDTO eContractValidationDTO = new ContractValidationDTO();
			eContractValidationDTO.setKey(obj.getMaazounCheckListLookupFk().getName());
			eContractValidationDTO.setValue(String.valueOf(obj.isValueDesc()));
			validationList.add(eContractValidationDTO.toString());
		}
		eMaazounCollectInfoDTO.setValidationSet(validationList);
		return eMaazounCollectInfoDTO;
	}

}
