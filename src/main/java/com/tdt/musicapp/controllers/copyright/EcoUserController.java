package com.tdt.musicapp.controllers.copyright;

import com.tdt.musicapp.controllers.BaseController;
import com.tdt.musicapp.dto.domain.EcoUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;

/**
 */


@Controller
@Slf4j
public class EcoUserController extends BaseController {

    @RequestMapping(value = {"/user/eco/profile", "/user/eco/profile/get"}, method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity getUser(String email) throws IOException {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.put("email", Arrays.asList(new String[]{email}));

        HttpEntity httpEntity = new HttpEntity(params, getHeaders(null));
        try {
            String url = env.getProperty("get_eco_profile_url");
            LinkedHashMap maps = restTemplate.postForObject(url, httpEntity, LinkedHashMap.class);
            String responseStatus = (String)maps.get("requestSuccess");
            if(responseStatus != null && !responseStatus.equalsIgnoreCase("true")){
                return ResponseEntity.badRequest().body(maps);
            }
            return ResponseEntity.ok(maps);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = {"/user/eco/profile/save"}, method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity saveUser(@RequestBody EcoUserDTO ecoUserDTO, HttpServletResponse response) throws IOException {

        System.out.println(ecoUserDTO.getEmail());
        try {
            MultiValueMap<String, String> multiValueMap = ecoUserDTO.toMap();
            HttpEntity httpEntity = new HttpEntity<MultiValueMap>(multiValueMap, getHeaders(null));
            String url = env.getProperty("save_eco_profile_url");
            LinkedHashMap maps = restTemplate.postForObject(url, httpEntity, LinkedHashMap.class);

            String responseStatus = (String)maps.get("requestSuccess");
            if(responseStatus != null && !responseStatus.equalsIgnoreCase("true")){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(maps);
            }
            return ResponseEntity.ok(maps);
        } catch (Exception e) {

            e.printStackTrace();
            response.setStatus(500);
            return null;
        }
    }

    @Override
    protected HttpSession getSession() {
        return session;
    }
}
