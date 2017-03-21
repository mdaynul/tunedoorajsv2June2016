package com.tdt.musicapp.dto.domain;

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

// {"userId":"aynulhaque","email":"mail.aynulhaque@gmail.com","prefferedContactMethod":"abcd",
// "address1":"10","address2":"15","city":"Dhaka","phone":"0123456",
// "salutation":"Mr","firstName":"Aynul","middleName":"","lastName":"Haque","
// challengeQuestion":"what?","answerChallengeQuestion":"nothing","zipCode":"null","alternatePhone":"564897",
// "id":2,"state":"Dhaka","country":"Bangladesh","password":"123"}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EcoUserDTO {

    protected String userId;
    protected String email;
    protected String prefferedContactMethod;
    protected String address1;
    protected String address2;
    protected String city;
    protected String phone;
    protected String salutation;
    protected String firstName;
    protected String middleName;
    protected String lastName;
    protected String challengeQuestion;
    protected String answerChallengeQuestion;
    protected String zipCode;
    protected String state;
    protected String country;
    protected String password;

    public MultiValueMap<String,String> toMap(){
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String,String> props = objectMapper.convertValue(this, Map.class);
//        props.remove("email");
//        props.remove("userId");
//        props.remove("firstName");
//        props.remove("middleName");
//        props.remove("lastName");
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.setAll(props);
        return map;
    }

}
