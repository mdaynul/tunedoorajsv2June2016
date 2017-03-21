package com.tdt.musicapp.dto.domain.transaction;

import com.braintreegateway.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.List;
import java.util.Map;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
    protected CalendarDTO createdAt;
    protected CalendarDTO updatedAt;
    protected String company;
    protected String email;
    protected String fax;
    protected String firstName;
    protected String id;
    protected String lastName;
    protected String phone;
    protected String website;
    protected Map<String, String> customFields;
    protected List<CreditCard> creditCards;
    protected List<PayPalAccount> paypalAccounts;
    protected List<ApplePayCard> applePayCards;
    protected List<AndroidPayCard> androidPayCards;
    protected List<AmexExpressCheckoutCard> amexExpressCheckoutCards;
    protected List<CoinbaseAccount> coinbaseAccounts;
    protected List<VenmoAccount> venmoAccounts;
    protected List<Address> addresses;
}
