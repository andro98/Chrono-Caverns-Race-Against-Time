package com.aman.payment.auth.model.payload;

import com.aman.payment.auth.service.CryptoMngrAuthService;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TogglePriceTierRequest implements AuthBasePayload<TogglePriceTierRequest> {
    private String id;

    @Override
    public TogglePriceTierRequest decrypt(CryptoMngrAuthService cryptoMngrAuthService) {
        return new TogglePriceTierRequest(
                cryptoMngrAuthService.decrypt(this.id)
        );
    }
}
