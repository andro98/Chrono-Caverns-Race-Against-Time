package com.aman.payment.auth.model.payload;

import com.aman.payment.auth.service.CryptoMngrAuthService;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AddEditSubServiceTierRequest implements AuthBasePayload<AddEditSubServiceTierRequest> {
    private String id;
    private String name;
    private String fees;
    private String subServiceFk;

    @Override
    public AddEditSubServiceTierRequest decrypt(CryptoMngrAuthService cryptoMngrAuthService) {
        return new AddEditSubServiceTierRequest(
                cryptoMngrAuthService.decrypt(id),
                cryptoMngrAuthService.decrypt(name),
                cryptoMngrAuthService.decrypt(fees),
                cryptoMngrAuthService.decrypt(subServiceFk)
        );
    }
}
