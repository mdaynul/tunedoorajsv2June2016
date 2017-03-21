package com.tdt.musicapp.dto.domain.transaction;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplePayDetailsDTO {
    protected String cardType;
    protected String paymentInstrumentName;
    protected String sourceDescription;
    protected String cardholderName;
    protected String expirationMonth;
    protected String expirationYear;
    protected String last4;
    protected String token;
}
