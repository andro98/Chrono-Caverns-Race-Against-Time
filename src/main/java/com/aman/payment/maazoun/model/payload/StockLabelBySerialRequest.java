package com.aman.payment.maazoun.model.payload;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockLabelBySerialRequest {
    private String serialNumber;
}
