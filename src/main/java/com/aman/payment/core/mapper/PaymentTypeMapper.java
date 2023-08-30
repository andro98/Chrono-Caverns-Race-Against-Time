package com.aman.payment.core.mapper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aman.payment.auth.service.CryptoMngrAuthService;
import com.aman.payment.core.model.dto.PaymentTypeDTO;
import com.aman.payment.core.model.lookup.PaymentType;

@Service
public class PaymentTypeMapper {

	@Autowired
	private CryptoMngrAuthService cryptoMngrAuthService;
	/*
	 * paymentType to paymentTypeDTOs
	 * paymentType to paymentTypeDTOs
	 * ****************************************************************************************************
	 */
	public PaymentTypeDTO paymentTypeToPaymentTypeDTO(PaymentType paymentType) {
        return createPaymentTypeDTO(paymentType);
    }
	
	public List<PaymentTypeDTO> paymentTypeToPaymentTypeDTOs(List<PaymentType> paymentTypes) {
		return paymentTypes.stream().filter(Objects::nonNull)
				.map(this::paymentTypeToPaymentTypeDTO).collect(Collectors.toList());
	}
	
	private PaymentTypeDTO createPaymentTypeDTO(PaymentType paymentType) {
		PaymentTypeDTO paymentTypeDTO = new PaymentTypeDTO();
		paymentTypeDTO.setId(cryptoMngrAuthService.encrypt(String.valueOf(paymentType.getId())));
		paymentTypeDTO.setName(cryptoMngrAuthService.encrypt(paymentType.getName()));
		return paymentTypeDTO;
	}

}
