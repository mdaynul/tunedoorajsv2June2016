package com.tdt.musicapp.controllers.registration;

import com.tdt.musicapp.controllers.BaseController;
import com.tdt.musicapp.dto.response.RegisterResponseDTO;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;

/**
 */

@Controller
public class SignupController extends BaseController{

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String goToRegisterPage() {
        return "registration/signup";
    }

    @RequestMapping(value = {"/signup", "/register"}, method = RequestMethod.POST)
    @ResponseBody
    public RegisterResponseDTO register(@RequestParam String email,
                                        @RequestParam String password,
                                        @RequestParam String name,
                                        HttpServletResponse response) {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.put("email", Arrays.asList(new String[]{email}));
        params.put("password", Arrays.asList(new String[]{password}));
        params.put("name", Arrays.asList(new String[]{name}));
        try {
            RegisterResponseDTO dto = restTemplate.postForObject(env.getProperty("register_url"),
                    new HttpEntity<MultiValueMap>(params, null),
                    RegisterResponseDTO.class);

            return RegisterResponseDTO.builder()
                    .message(dto.getMessage())
                    .requestSuccess(dto.isRequestSuccess())
                    .build();
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
