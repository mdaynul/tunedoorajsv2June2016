package com.tdt.musicapp.dto.domain.transaction;

import com.braintreegateway.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tdt.musicapp.dto.domain.ActiveServiceDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionDTO {
    protected List<AddOn> addOns;
    protected BigDecimal amount;
    protected String avsErrorResponseCode;
    protected String avsPostalCodeResponseCode;
    protected String avsStreetAddressResponseCode;
    protected AddressDTO billingAddress;
    protected String channel;
    protected CalendarDTO createdAt;
    protected CreditCardDTO creditCard;
    protected String currencyIsoCode;
    protected CustomerDTO customer;
    protected Map<String, String> customFields;
    protected String cvvResponseCode;
    protected DisbursementDetailsDTO disbursementDetails;
    protected String musicTitle;
    protected List<ActiveServiceDTO> activeServices;
    protected DescriptorDTO descriptor;
//    protected List<Discount> discounts;
//    protected TransactionDTO.EscrowStatus escrowStatus;
//    protected TransactionDTO.GatewayRejectionReason gatewayRejectionReason;
    protected String id;
    protected String merchantAccountId;
    protected String orderId;
    protected PayPalDetailsDTO paypalDetails;
    protected ApplePayDetailsDTO applePayDetails;
    protected AndroidPayDetailsDTO androidPayDetails;
//    protected AmexExpressCheckoutDetails amexExpressCheckoutDetails;
//    protected VenmoAccountDetails venmoAccountDetails;
    protected String planId;
    protected String processorAuthorizationCode;
    protected String processorResponseCode;
    protected String processorResponseText;
    protected String processorSettlementResponseCode;
    protected String processorSettlementResponseText;
    protected String additionalProcessorResponse;
    protected String voiceReferralNumber;
    protected String purchaseOrderNumber;
    protected Boolean recurring;
    protected String refundedTransactionId;
    protected String refundId;
    protected List<String> refundIds;
    protected String settlementBatchId;
    protected AddressDTO shippingAddress;
//    protected TransactionDTO.Status status;
//    protected List<StatusEvent> statusHistory;
    protected String subscriptionId;
//    protected Subscription subscription;
    protected BigDecimal taxAmount;
    protected Boolean taxExempt;
//    protected TransactionDTO.Type type;
//    protected CalendarDTO updatedAt;
    protected BigDecimal serviceFeeAmount;
    protected String paymentInstrumentType;
//    protected RiskData riskData;
//    protected ThreeDSecureInfo threeDSecureInfo;
//    protected CoinbaseDetails coinbaseDetails;
    protected String authorizedTransactionId;
    protected List<String> partialSettlementTransactionIds;

    public static enum Type {
        CREDIT("credit"),
        SALE("sale"),
        UNRECOGNIZED("unrecognized");

        protected final String name;

        private Type(String name) {
            this.name = name;
        }

        public String toString() {
            return this.name;
        }
    }

    public static enum IndustryType {
        LODGING("lodging"),
        TRAVEL_CRUISE("travel_cruise");

        protected final String name;

        private IndustryType(String name) {
            this.name = name;
        }

        public String toString() {
            return this.name;
        }
    }

    public static enum Status {
        AUTHORIZATION_EXPIRED,
        AUTHORIZED,
        AUTHORIZING,
        FAILED,
        GATEWAY_REJECTED,
        PROCESSOR_DECLINED,
        SETTLED,
        SETTLEMENT_CONFIRMED,
        SETTLEMENT_DECLINED,
        SETTLEMENT_PENDING,
        SETTLING,
        SUBMITTED_FOR_SETTLEMENT,
        UNRECOGNIZED,
        VOIDED;

        private Status() {
        }
    }

    public static enum Source {
        API("api"),
        CONTROL_PANEL("control_panel"),
        RECURRING("recurring"),
        UNRECOGNIZED("unrecognized");

        protected final String name;

        private Source(String name) {
            this.name = name;
        }

        public String toString() {
            return this.name;
        }
    }

    public static enum GatewayRejectionReason {
        APPLICATION_INCOMPLETE("application_incomplete"),
        AVS("avs"),
        AVS_AND_CVV("avs_and_cvv"),
        CVV("cvv"),
        DUPLICATE("duplicate"),
        FRAUD("fraud"),
        THREE_D_SECURE("three_d_secure"),
        UNRECOGNIZED("unrecognized");

        protected final String name;

        private GatewayRejectionReason(String name) {
            this.name = name;
        }

        public String toString() {
            return this.name;
        }
    }

    public static enum EscrowStatus {
        HELD,
        HOLD_PENDING,
        RELEASE_PENDING,
        RELEASED,
        REFUNDED,
        UNRECOGNIZED;

        private EscrowStatus() {
        }
    }

    public static enum CreatedUsing {
        FULL_INFORMATION("full_information"),
        TOKEN("token"),
        UNRECOGNIZED("unrecognized");

        protected final String name;

        private CreatedUsing(String name) {
            this.name = name;
        }

        public String toString() {
            return this.name;
        }
    }
}
