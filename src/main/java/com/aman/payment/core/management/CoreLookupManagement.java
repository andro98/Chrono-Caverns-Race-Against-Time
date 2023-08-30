package com.aman.payment.core.management;

import com.aman.payment.core.model.dto.PaymentTypeDTO;

public interface CoreLookupManagement {
	
	public PaymentTypeDTO getPaymentTypeById(String paymentTypeId);
}
