package com.tdt.musicapp.dto.domain.transaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AndroidPayDetailsDTO {
    protected String cardType;
    protected String last4;
    protected String sourceCardType;
    protected String sourceCardLast4;
    protected String sourceDescription;
    protected String virtualCardType;
    protected String virtualCardLast4;
    protected String expirationMonth;
    protected String expirationYear;
    protected String token;
    protected String googleTransactionId;
    protected String bin;
    protected String imageUrl;
}
