package com.tdt.musicapp.dto.domain.transaction;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Calendar;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DisbursementDetailsDTO {
    protected Calendar disbursementDate;
    protected String settlementCurrencyIsoCode;
    protected Boolean fundsHeld;
    protected Boolean success;
    protected BigDecimal settlementCurrencyExchangeRate;
    protected BigDecimal settlementAmount;

}
