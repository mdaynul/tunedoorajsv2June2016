package com.tdt.musicapp.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.util.Arrays;

public abstract class BaseController {

    @Autowired
    protected Environment env;
    @Autowired
    protected RestTemplate restTemplate;
    @Autowired
    protected HttpSession session;

    protected HttpHeaders getHeaders(String userAgent) {

        HttpHeaders headers = new HttpHeaders();
        headers.put("authCode", Arrays.asList((String)getSession().getAttribute("authCode")));
        headers.put("authToken", Arrays.asList((String) getSession().getAttribute("authToken")));
        headers.put("userAgent", Arrays.asList(userAgent != null ? userAgent : (String) getSession().getAttribute("userAgent")));
        return headers;
    }

    protected abstract HttpSession getSession();
}
