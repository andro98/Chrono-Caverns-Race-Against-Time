package com.aman.payment.maazoun.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.JSONObject;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SupplyOrderDetailsDTO {

    private String id;
    private String createAt;
    private String createBy;
    private String bookTypeId;
    private String bookTypeName;
    private String sectorId;
    private String sectorName;
    private String attUrl;
    private String currentBookTypeCount;
    private String remainingBookTypeCount;
    private String bookCount;
    private String refSupplyOrderNumber;
    private String bookTierId;
    private String bookTierName;
    private String bookTierPrice;
    @Override
    public String toString() {
        return new JSONObject(this).toString();
    }
}
