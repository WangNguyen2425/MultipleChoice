package com.demo.app.controller;

import com.demo.app.dto.auth.AuthenticationRequest;
import com.demo.app.dto.auth.RegisterRequest;
import com.demo.app.exception.FieldExistedException;
import com.demo.app.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@RestController
@RequestMapping(path = "/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping(path = "/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody @Valid final AuthenticationRequest request) {
        return ResponseEntity.ok().body(authService.login(request));
    }


    @PostMapping(path = "/signup")
    public ResponseEntity<?> registerUser(@RequestBody @Valid final RegisterRequest request) throws FieldExistedException {
        return ResponseEntity.ok().body(authService.register(request));
    }

    @PostMapping(path = "/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        authService.refreshToken(request, response);
    }

}
