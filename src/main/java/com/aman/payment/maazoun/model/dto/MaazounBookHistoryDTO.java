package com.aman.payment.maazoun.model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.JSONObject;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MaazounBookHistoryDTO {

    private String bookType;
    private String bookTierPrice;
    private String serialNumber;
    private String financialNumber;
    private String bookLastStatus;
    private long numberOfContracts;

    private String suppliedDateBy;
    private String suppliedReviewedDateBy;
    private String custody;
    private String soldDateBy;
    private String maazounNameAndType;
    private String maazounNationalId;

    private String sectorName;
    private String deliveryDateBy;
    private String deliveryApprovedDateBy;

    private List<String> contracts;

    @Override
    public String toString() {

        return new JSONObject(this).toString();

    }

}