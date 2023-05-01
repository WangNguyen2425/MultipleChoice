package com.demo.app.controller;

import com.demo.app.dto.auth.AuthenticationRequest;
import com.demo.app.dto.auth.AuthenticationResponse;
import com.demo.app.dto.message.ResponseMessage;
import com.demo.app.exception.FieldExistedException;
import com.demo.app.model.User;
import com.demo.app.service.AuthService;
import com.demo.app.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "/api/v1/")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final UserService userService;

    @PostMapping(path = "/auth/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody @Valid final AuthenticationRequest request) {

        AuthenticationResponse response = authService.login(request);
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, response.getToken()).body(response);
    }


    @PostMapping(path = "/auth/signup")
    public ResponseEntity<?> registerUser(@RequestBody @Valid final AuthenticationRequest userRegister) throws FieldExistedException {
        User user = userService.saveUser(userRegister);
        String message = String.format("User %s register successfully !", user.getUsername());
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.CREATED);
    }


}
