package com.aman.payment.auth.mapper;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aman.payment.auth.model.SubService;
import com.aman.payment.auth.model.SubServiceQuota;
import com.aman.payment.auth.model.dto.JwtAuthServiceDTO;
import com.aman.payment.auth.model.dto.ServiceDTO;
import com.aman.payment.auth.model.dto.SubServiceDTO;
import com.aman.payment.auth.model.dto.SubServiceQuotaDTO;
import com.aman.payment.auth.model.lookup.ServiceType;
import com.aman.payment.auth.service.CryptoMngrAuthService;

@Service
public class SubServiceQuotaMapper {

    @Autowired
    private CryptoMngrAuthService cryptoMngrAuthService;

    /*
     * subServiceQuota to subServiceQuotaDTO
     * subServiceQuotas to subServiceQuotaDTOs
     * ****************************************************************************************************
     */
    public SubServiceQuotaDTO subServiceQuotaTosubServiceQuotaDTO(SubServiceQuota subServiceQuota) {
        return createSubServiceQuotaDTO(subServiceQuota);
    }

    public List<SubServiceQuotaDTO> subServiceQuotaTosubServiceQuotaDTOs(List<SubServiceQuota> subServiceQuotas) {
        return subServiceQuotas.stream().filter(Objects::nonNull)
                .map(this::subServiceQuotaTosubServiceQuotaDTO).collect(Collectors.toList());
    }

    public Set<SubServiceQuotaDTO> subServiceQuotaTosubServiceQuotaDTOs(Set<SubServiceQuota> subServiceQuotas) {
        return subServiceQuotas.stream().filter(Objects::nonNull)
                .map(this::subServiceQuotaTosubServiceQuotaDTO).collect(Collectors.toSet());
    }

    private SubServiceQuotaDTO createSubServiceQuotaDTO(SubServiceQuota subServiceQuota) {
        SubServiceQuotaDTO subServiceQuotaDTO = new SubServiceQuotaDTO();
        subServiceQuotaDTO.setId(String.valueOf(subServiceQuota.getId()));
        subServiceQuotaDTO.setFees(String.valueOf(subServiceQuota.getFees()));
        subServiceQuotaDTO.setName(subServiceQuota.getName());
        subServiceQuotaDTO.setFeesType(subServiceQuota.getFeesType());
        subServiceQuotaDTO.setMidAccount(cryptoMngrAuthService.decrypt(subServiceQuota.getMidAccount()));
        subServiceQuotaDTO.setMidBank(cryptoMngrAuthService.decrypt(subServiceQuota.getMidBank()));
        subServiceQuotaDTO.setMidBenficiary(subServiceQuota.getBeneficiary());
        subServiceQuotaDTO.setStatus(subServiceQuota.getStatusFk());
        subServiceQuotaDTO.setSubServiceId(String.valueOf(subServiceQuota.getSubServiceFk().getId()));
        subServiceQuotaDTO.setSubServiceName(subServiceQuota.getSubServiceFk().getName());
        subServiceQuotaDTO.setDescription(subServiceQuota.getDescription());
        if(subServiceQuota.getSubServicePriceTierFk() != null){
            subServiceQuotaDTO.setSubServiceTierId(String.valueOf(subServiceQuota.getSubServicePriceTierFk().getId()));
        }
        return subServiceQuotaDTO;
    }


}
