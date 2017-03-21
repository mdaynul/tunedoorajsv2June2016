package com.tdt.musicapp.controllers.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 */

@Controller
public class LogoutController {
    @Autowired
    private HttpSession session;

    @RequestMapping(value = {"/logout"}, method = {RequestMethod.GET, RequestMethod.POST})
    String index(HttpServletResponse response){
        session.invalidate();
        return "redirect:/";
    }
}
