package com.tdt.musicapp.dto.domain.transaction;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayPalDetailsDTO {
    protected String payerEmail;
    protected String paymentId;
    protected String authorizationId;
    protected String token;
    protected String imageUrl;
    protected String debugId;
    protected String payeeEmail;
    protected String customField;
    protected String payerId;
    protected String payerFirstName;
    protected String payerLastName;
    protected String sellerProtectionStatus;
    protected String captureId;
    protected String refundId;
    protected String transactionFeeAmount;
    protected String transactionFeeCurrencyIsoCode;
    protected String description;

}
