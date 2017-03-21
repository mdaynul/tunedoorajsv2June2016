package com.tdt.musicapp.dto.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;

/**
 */

//[{"createdBy":null,"suggestedCost":75,"isActive":"Y","createdAt":null,"currency":"$","id":1,
// "serviceName":"Copyright","description":"Copyright your music and keep your music safe."},
// {"createdBy":null,"suggestedCost":1,"isActive":"Y","createdAt":null,"currency":"$","id":2,
// "serviceName":"Lead Sheet","description":"Generate lead sheet to grow in music industry"}]

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActiveServiceDTO {

    protected String id;
    protected String serviceName;
    protected String description;
    protected String isActive;
    protected String suggestedCost;
//    protected String createdAt;
//    protected String createdBy;

}
