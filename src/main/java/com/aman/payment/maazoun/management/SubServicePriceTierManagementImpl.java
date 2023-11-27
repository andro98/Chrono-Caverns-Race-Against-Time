package com.aman.payment.maazoun.management;

import com.aman.payment.auth.model.SubServicePriceTier;
import com.aman.payment.auth.service.SubServicePriceTierService;
import com.aman.payment.maazoun.service.MaazounBookStockLabelService;
import com.aman.payment.maazoun.service.MaazounBookWarehouseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class SubServicePriceTierManagementImpl implements SubServicePriceTierManagement {

    private final SubServicePriceTierService subServicePriceTierService;

    private final MaazounBookStockLabelService maazounBookStockLabelService;

    private final MaazounBookWarehouseService maazounBookWarehouseService;

    SubServicePriceTier getTierBySerialNumber(String serialNumber) {
        return maazounBookStockLabelService
                .findByLabelCode(serialNumber)
                .flatMap(maazounBookStockLabel -> subServicePriceTierService.findById(maazounBookStockLabel.getBookTierId()))
                .orElseThrow(() -> new RuntimeException("No tier found for serial number: " + serialNumber));
    }

    @Override
    public Double getFeesByTierId(Long tierId) {
        return subServicePriceTierService.findById(tierId)
                .map(SubServicePriceTier::getFees)
                .orElseThrow(() -> new RuntimeException("No tier found for id: " + tierId));
    }
}
