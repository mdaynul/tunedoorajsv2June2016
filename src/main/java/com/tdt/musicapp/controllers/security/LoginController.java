package com.tdt.musicapp.controllers.security;

import com.tdt.musicapp.dto.domain.LoginDTO;
import com.tdt.musicapp.dto.response.LoginResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;

/**
 */

@Controller
public class LoginController {
    @Autowired
    private Environment env;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private HttpSession session;

    @RequestMapping(value = {"/login"}, method = {RequestMethod.GET})
    String index(){
        return "login/login";
    }

    @RequestMapping(value = {"/login"}, method = {RequestMethod.POST})
    @ResponseBody
    public LoginResponseDTO login(@RequestParam String email,
                                  @RequestParam String password,
                                  @RequestParam String userAgent,
                                  HttpServletResponse response) {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.put("email", Arrays.asList(new String[]{email}));
        params.put("password", Arrays.asList(new String[]{password}));
        HttpHeaders headers = new HttpHeaders();
        headers.put("userAgent", Arrays.asList(new String[]{userAgent}));
        try {
            LoginDTO loginDTO = restTemplate.postForObject(env.getProperty("login_url"),
                    new HttpEntity<MultiValueMap>(params, headers),
                    LoginDTO.class);
            session.setAttribute("authCode", loginDTO.getAuthCode());
            session.setAttribute("authToken", loginDTO.getAuthToken());
            session.setAttribute("userAgent", userAgent);

//            TODO refactor
            LoginResponseDTO loginResponseDTO = LoginResponseDTO
                    .builder()
                    .authCode(loginDTO.getAuthCode())
                    .authToken(loginDTO.getAuthToken())
                    .build();
            return loginResponseDTO;

        } catch (Exception e) {

            e.printStackTrace();
            response.setStatus(500);
            return null;
        }
    }

}
