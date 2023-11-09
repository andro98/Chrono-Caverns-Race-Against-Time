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
public class StockLabelDTO {
    private Long stockLabelId;
    private String labelCode;
    private String statusFk;
    private Long locationId;
    private String locationName;
    private String createdBy;
    private String createdAt;
    private String updatedAt;
    private String updatedBy;
    private String bookTypeId;
    private double bookTierFees;

    @Override
    public String toString() {
        return new JSONObject(this).toString();
    }
}
