package com.aman.payment.auth.mapper;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aman.payment.auth.model.Merchant;
import com.aman.payment.auth.model.dto.MerchantDTO;
import com.aman.payment.auth.service.CryptoMngrAuthService;

@Service
public class MerchantMapper {
	
	/*
	 * merchant to merchantDTO
	 * merchant to merchantDTOs
	 * ****************************************************************************************************
	 */
	public MerchantDTO merchantToMerchantDTO(Merchant merchant) {
        return createMerchantDTO(merchant);
    }
	
	public List<MerchantDTO> merchantsToMerchantDTOs(List<Merchant> merchants) {
		return merchants.stream().filter(Objects::nonNull)
				.map(this::merchantToMerchantDTO).collect(Collectors.toList());
	}
	
	public Set<MerchantDTO> merchantsToMerchantDTOs(Set<Merchant> merchants) {
		return merchants.stream().filter(Objects::nonNull)
				.map(this::merchantToMerchantDTO).collect(Collectors.toSet());
	}
	
	private MerchantDTO createMerchantDTO(Merchant merchant) {
		MerchantDTO merchantDTO = new MerchantDTO();
		merchantDTO.setAddress(merchant.getAddress());
		merchantDTO.setCode(merchant.getCode());
		merchantDTO.setDescription(merchant.getDescription());
		merchantDTO.setEmail(merchant.getEmail());
		merchantDTO.setFax(merchant.getFax());
		merchantDTO.setId(String.valueOf(merchant.getId()));
		merchantDTO.setMobile(merchant.getMobile());
		merchantDTO.setName(merchant.getName());
		merchantDTO.setPhone1(merchant.getPhone1());
		merchantDTO.setPhone2(merchant.getPhone2());
		merchantDTO.setStatus(merchant.getStatusFk());
		
		return merchantDTO;
	}

}
