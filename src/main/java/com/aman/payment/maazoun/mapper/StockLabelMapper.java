package com.aman.payment.maazoun.mapper;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.aman.payment.auth.model.SubServicePriceTier;
import com.aman.payment.auth.service.SubServicePriceTierService;
import org.springframework.stereotype.Service;

import com.aman.payment.maazoun.model.MaazounBookStockLabel;
import com.aman.payment.maazoun.model.dto.StockLabelDTO;
import com.aman.payment.util.Util;

@Service
public class StockLabelMapper {

    final SubServicePriceTierService subServicePriceTierService;

    public StockLabelMapper(SubServicePriceTierService subServicePriceTierService) {
        this.subServicePriceTierService = subServicePriceTierService;
    }

    /*
     * MaazounBookStockLabel to StockLabelDTO
     * MaazounBookStockLabels to StockLabelDTOs
     * ****************************************************************************************************
     */
    public StockLabelDTO maazounBookStockLabelToStockLabelDTO(MaazounBookStockLabel maazounBookStockLabel) {
        if (maazounBookStockLabel != null) {
            return createStockLabelDTO(maazounBookStockLabel);

        } else return null;
    }

    public List<StockLabelDTO> maazounBookStockLabelsToStockLabelDTOs(List<MaazounBookStockLabel> maazounBookStockLabels) {
        return maazounBookStockLabels.stream().filter(Objects::nonNull)
                .map(this::maazounBookStockLabelToStockLabelDTO).collect(Collectors.toList());
    }

    private StockLabelDTO createStockLabelDTO(MaazounBookStockLabel maazounBookStockLabel) {
        StockLabelDTO stockLabelDTO = new StockLabelDTO();
        stockLabelDTO.setCreatedAt(Util.dateFormatReport(maazounBookStockLabel.getCreatedAt()));
        stockLabelDTO.setCreatedBy(maazounBookStockLabel.getCreatedBy());
        stockLabelDTO.setStockLabelId(maazounBookStockLabel.getId());
        stockLabelDTO.setLabelCode(maazounBookStockLabel.getLabelCode());
        stockLabelDTO.setLocationId(maazounBookStockLabel.getLocationId());
        stockLabelDTO.setStatusFk(maazounBookStockLabel.getStatusFk());
        stockLabelDTO.setBookTypeId(String.valueOf(maazounBookStockLabel.getBookTypeId()));
        if (maazounBookStockLabel.getUpdatedAt() != null)
            stockLabelDTO.setUpdatedAt(Util.dateFormatReport(maazounBookStockLabel.getUpdatedAt()));
        stockLabelDTO.setUpdatedBy(maazounBookStockLabel.getUpdatedBy());

        Optional<SubServicePriceTier> subServicePriceTier = subServicePriceTierService.findById(maazounBookStockLabel.getBookTierId());
        subServicePriceTier.ifPresent(servicePriceTier -> stockLabelDTO.setBookTierFees(servicePriceTier.getFees()));

        return stockLabelDTO;
    }


}
