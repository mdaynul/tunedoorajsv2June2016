package com.tdt.musicapp.dto.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.NumberUtils;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentCheckoutDTO {

    protected String musicId;
    protected String musicName;
    protected String services;
    protected String amount;
    protected String nonce;
//    protected String type;
//    protected List<Object> details;

    public MultiValueMap<String,String> toMap(){
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String,String> props = objectMapper.convertValue(this, Map.class);
        props.put("payment_method_nonce", this.nonce);
//        props.remove("nonce");
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.setAll(props);
        return map;
    }

    // todo - Aynul :
    // why Integer? .. i found id is integer.
    // Long should be used instead
//
//    Set<String> idList = StringUtils.commaDelimitedListToSet(services);
//    List<Integer> newList = new ArrayList<Integer>(idList.size());
//    for (String id : idList) {
//        newList.add(Integer.valueOf(id));
//    }

    String[] getServiceIds(){
        return StringUtils.commaDelimitedListToStringArray(services);
    }
}
