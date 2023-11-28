package com.aman.payment.maazoun.management;

import com.aman.payment.auth.model.SubServicePriceTier;

public interface SubServicePriceTierManagement {
    Double getFeesByTierId(Long tierId);

    Long getFirstTierId(Long serviceId);

    SubServicePriceTier findByID(Long id);
}
