package com.demo.app.controller;

import com.demo.app.model.Admin;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(name = "/admin")
public class AdminController {

    @GetMapping(name = "/login")
    public ModelAndView adminLoginPage(){
        ModelAndView mav = new ModelAndView("admin-login");
        mav.addObject("admin", new Admin());
        return mav;
    }

}
