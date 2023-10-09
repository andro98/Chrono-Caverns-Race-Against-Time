package com.aman.payment.maazoun.mapper;

import java.util.*;
import java.util.stream.Collectors;

import com.aman.payment.auth.model.SubServicePriceTier;
import com.aman.payment.auth.service.SubServicePriceTierService;
import com.aman.payment.maazoun.model.MaazounProfile;
import com.aman.payment.maazoun.model.dto.MaazounProfileDTO;
import org.springframework.stereotype.Service;

import com.aman.payment.core.util.UtilCore;
import com.aman.payment.maazoun.model.SupplyOrderDetails;
import com.aman.payment.maazoun.model.dto.SupplyOrderDetailsDTO;
import com.aman.payment.maazoun.model.payload.AddSupplyOrderDetails;
import com.aman.payment.maazoun.model.payload.AddSupplyOrderDetailsListRequest;
import com.aman.payment.maazoun.model.SupplyOrderDetails;

@Service
public class SupplyOrderDetailsMapper {

    private final SubServicePriceTierService subServicePriceTierService;

    public SupplyOrderDetailsMapper(SubServicePriceTierService subServicePriceTierService) {
        // TODO Auto-generated constructor stub
        this.subServicePriceTierService = subServicePriceTierService;
    }

    public SupplyOrderDetailsDTO supplyOrderDetailsToSupplyOrderDetailsDTO(
            SupplyOrderDetails supplyOrderDetails) {
        if (supplyOrderDetails != null) {
            return createSupplyOrderDetailsDTO(supplyOrderDetails);

        } else
            return null;
    }

    public List<SupplyOrderDetailsDTO> supplyOrdersDetailsToSupplyOrderDetailsDTOs(
            List<SupplyOrderDetails> maazounBookSupplyOrderSet) {
        return maazounBookSupplyOrderSet.stream().filter(Objects::nonNull)
                .map(this::supplyOrderDetailsToSupplyOrderDetailsDTO)
                .collect(Collectors.toList());
    }

    private SupplyOrderDetailsDTO createSupplyOrderDetailsDTO(SupplyOrderDetails supplyOrderDetails) {

        SupplyOrderDetailsDTO supplyOrderDetailsDTO = new SupplyOrderDetailsDTO();
        supplyOrderDetailsDTO.setCreateAt(String.valueOf(supplyOrderDetails.getCreatedAt()));
        supplyOrderDetailsDTO.setCreateBy(supplyOrderDetails.getCreatedBy());
        supplyOrderDetailsDTO.setId(String.valueOf(supplyOrderDetails.getId()));
        supplyOrderDetailsDTO.setBookTypeId(supplyOrderDetails.getBookTypeFK());
        supplyOrderDetailsDTO.setBookTypeName(supplyOrderDetails.getBookType());
        supplyOrderDetailsDTO.setSectorId(String.valueOf(supplyOrderDetails.getSectorFK()));
        supplyOrderDetailsDTO.setSectorName(supplyOrderDetails.getSectorName());
        supplyOrderDetailsDTO.setBookTierId(supplyOrderDetails.getBootTierId());
//		supplyOrderDetailsDTO.setAttUrl(maazounBookSupplyOrder.getSupplyOrderDetailsUrl());
        supplyOrderDetailsDTO.setBookCount(String.valueOf(supplyOrderDetails.getBookTypeCount()));
        supplyOrderDetailsDTO.setRemainingBookTypeCount(String.valueOf(supplyOrderDetails.getRemainingBookTypeCount()));
        supplyOrderDetailsDTO.setRefSupplyOrderNumber(supplyOrderDetails.getSupplyOrderFk().getRefSupplyOrderNumber());

        Optional<SubServicePriceTier> tier = subServicePriceTierService.findById(Long.valueOf(supplyOrderDetails.getBootTierId()));
        if (tier.isPresent()) {
            supplyOrderDetailsDTO.setBookTierName(tier.get().getName());
            supplyOrderDetailsDTO.setBookTierId(String.valueOf(tier.get().getId()));
            supplyOrderDetailsDTO.setBookTierPrice(String.valueOf(tier.get().getFees()));
        }

        return supplyOrderDetailsDTO;
    }

}
