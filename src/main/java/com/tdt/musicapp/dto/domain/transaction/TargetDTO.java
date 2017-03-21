package com.tdt.musicapp.dto.domain.transaction;

import com.braintreegateway.AddOn;
import com.braintreegateway.StatusEvent;
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
public class TargetDTO {
    protected TransactionDTO target;
}
