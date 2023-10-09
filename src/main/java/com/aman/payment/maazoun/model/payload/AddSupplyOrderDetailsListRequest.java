package com.aman.payment.maazoun.model.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddSupplyOrderDetailsListRequest {

    private String bootTypeId;
    private String bootTypeName;
    private String count;
    private String subServicePriceTierId;
    private String bootTierName;

}
