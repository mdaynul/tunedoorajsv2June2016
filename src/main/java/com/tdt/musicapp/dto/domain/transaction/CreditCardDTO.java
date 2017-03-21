package com.tdt.musicapp.dto.domain.transaction;

import com.braintreegateway.Address;
import com.braintreegateway.CreditCardVerification;
import com.braintreegateway.Subscription;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class CreditCardDTO {
    public static final String VALUE_YES = "Yes";
    public static final String VALUE_NO = "No";
    public static final String VALUE_UNKNOWN = "Unknown";
    protected Address billingAddress;
    protected String bin;
    protected String cardholderName;
    protected String cardType;
    protected Calendar createdAt;
    protected String customerId;
    protected String customerLocation;
    protected String expirationMonth;
    protected String expirationYear;
    protected Boolean isDefault;
    protected Boolean isVenmoSdk;
    protected Boolean isExpired;
    protected String imageUrl;
    protected String last4;
    protected String commercial;
    protected String debit;
    protected String durbinRegulated;
    protected String healthcare;
    protected String payroll;
    protected String prepaid;
    protected String countryOfIssuance;
    protected String issuingBank;
    protected String uniqueNumberIdentifier;
    protected List<Subscription> subscriptions;
    protected String token;
    protected Calendar updatedAt;
    protected CreditCardVerification verification;
}
