package com.demo.app.controller;

import com.demo.app.dto.auth.AuthenticationRequest;
import com.demo.app.dto.auth.RegisterRequest;
import com.demo.app.exception.FieldExistedException;
import com.demo.app.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "/api/v1/")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping(path = "/auth/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody @Valid final AuthenticationRequest request) {
        return ResponseEntity.ok().body(authService.login(request));
    }


    @PostMapping(path = "/auth/signup")
    public ResponseEntity<?> registerUser(@RequestBody @Valid final RegisterRequest request) throws FieldExistedException {
        return ResponseEntity.ok().body(authService.register(request));
    }


}
