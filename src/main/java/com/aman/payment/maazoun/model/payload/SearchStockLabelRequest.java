package com.aman.payment.maazoun.model.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SearchStockLabelRequest {
    private int pageNo;
    private int pageSize;
    private String sortBy;
    private Long total;
    private String status;
    private Long locationId;
    private Long bookTypeId;
    private Long bookTierId;
}