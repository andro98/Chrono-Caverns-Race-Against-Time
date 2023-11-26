package com.aman.payment.auth.model.dto;

import com.aman.payment.auth.service.CryptoMngrAuthService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.JSONObject;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubServicePriceTierDTO implements AuthBaseDTO<SubServicePriceTierDTO> {

    private String id;
    private String name;
    private String fees;
    private String description;
    private String subServiceId;
    private String subServiceName;
    private String currentQuotaFees;
    private String subServiceContractCount;

    @Override
    public SubServicePriceTierDTO encrypt(CryptoMngrAuthService cryptoMngrAuthService) {
        return new SubServicePriceTierDTO(
                cryptoMngrAuthService.encrypt(id),
                cryptoMngrAuthService.encrypt(name),
                cryptoMngrAuthService.encrypt(fees),
                cryptoMngrAuthService.encrypt(description),
                cryptoMngrAuthService.encrypt(subServiceId),
                cryptoMngrAuthService.encrypt(subServiceName),
                cryptoMngrAuthService.encrypt(currentQuotaFees),
                cryptoMngrAuthService.encrypt(subServiceContractCount)
        );
    }

    @Override
    public String toString() {
        return new JSONObject(this).toString();
    }
}
