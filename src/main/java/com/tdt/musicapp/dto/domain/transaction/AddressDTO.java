package com.tdt.musicapp.dto.domain.transaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
    protected String company;
    protected String countryCodeAlpha2;
    protected String countryCodeAlpha3;
    protected String countryCodeNumeric;
    protected String countryName;
    protected CalendarDTO createdAt;
    protected String customerId;
    protected String extendedAddress;
    protected String firstName;
    protected String id;
    protected String lastName;
    protected String locality;
    protected String postalCode;
    protected String region;
    protected String streetAddress;
    protected Calendar updatedAt;

}
