package com.aman.payment.maazoun.model.payload;

import java.util.List;

import com.aman.payment.maazoun.model.MaazounBookQuota;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel(value = "BookListRequest", description = "Add Book List Request DTO Payload")
@Data
public class BookListRequest extends MaazounBookQuota {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Long typeId;
    private String type;
    private String serialNumber;
    private Long bookWarehouseId;
    private Long contractCount;
    private String bookFinancialNumber;
    private Double fees;
    private List<AddContractListRequest> contracts;
    private String maazouniaChurchId;
    private String maazouniaChurchNameType;
    private Long sectorId;
}