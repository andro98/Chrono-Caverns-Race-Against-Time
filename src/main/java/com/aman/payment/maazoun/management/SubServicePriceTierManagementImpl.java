package com.aman.payment.maazoun.management;

import com.aman.payment.auth.model.SubService;
import com.aman.payment.auth.model.SubServicePriceTier;
import com.aman.payment.auth.service.SubServicePriceTierService;
import com.aman.payment.auth.service.SubServiceService;
import com.aman.payment.maazoun.service.MaazounBookStockLabelService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class SubServicePriceTierManagementImpl implements SubServicePriceTierManagement {

    private final SubServiceService subServiceService;

    private final SubServicePriceTierService subServicePriceTierService;

    private final MaazounBookStockLabelService maazounBookStockLabelService;

//    private final MaazounBookWarehouseService maazounBookWarehouseService;

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

    @Override
    public Long getFirstTierId(Long serviceId) {
        SubService subService = subServiceService.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("No service found for id: " + serviceId));
        return subServicePriceTierService
                .getSubServiceBySubServiceFk(subService)
                .stream()
                .min((tier1, tier2) -> tier1.getId().compareTo(tier2.getId()))
                .orElseThrow(() -> new RuntimeException("No tier found for service id: " + serviceId))
                .getId();
    }

    @Override
    public SubServicePriceTier findByID(Long id) {
        return subServicePriceTierService.findById(id)
                .orElseThrow(() -> new RuntimeException("No tier found for id: " + id));
    }
}
