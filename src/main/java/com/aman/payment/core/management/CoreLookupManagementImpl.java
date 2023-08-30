package com.aman.payment.core.management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aman.payment.core.mapper.PaymentTypeMapper;
import com.aman.payment.core.model.dto.PaymentTypeDTO;
import com.aman.payment.core.service.PaymentTypeService;


@Component
public class CoreLookupManagementImpl implements CoreLookupManagement {
    

	private final PaymentTypeService paymentTypeService;
	private final PaymentTypeMapper paymentTypeMapper;
	
	@Autowired
	public CoreLookupManagementImpl(PaymentTypeService paymentTypeService, PaymentTypeMapper paymentTypeMapper) {
		super();
		this.paymentTypeService = paymentTypeService;
		this.paymentTypeMapper = paymentTypeMapper;
	}

	
	@Override
	public PaymentTypeDTO getPaymentTypeById(String paymentTypeId) {
		return paymentTypeMapper.paymentTypeToPaymentTypeDTO(paymentTypeService.findById(Long.parseLong(paymentTypeId)).get());
	}

	
}
