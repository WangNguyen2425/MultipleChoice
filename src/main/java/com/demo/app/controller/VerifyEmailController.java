package com.demo.app.controller;

import com.demo.app.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class VerifyEmailController {
    private final AuthService authService;

    @GetMapping(path = "/verify-email")
    public ModelAndView verifyEmail(@RequestParam(name = "token") String verifyToken){
        authService.activateUserAccount(verifyToken);
        return new ModelAndView("activate-email");
    }
}
