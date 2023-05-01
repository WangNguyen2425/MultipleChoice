package com.demo.app.controller;

import com.demo.app.dto.auth.AuthenticationRequest;
import com.demo.app.dto.auth.AuthenticationResponse;
import com.demo.app.service.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
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

    @PostMapping(path = "/auth/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody @Valid final AuthenticationRequest request) {
        AuthenticationResponse response = authService.login(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


//    @PostMapping(path = "/auth/signup")
//    public ResponseEntity<?> registerUser(@RequestBody @Valid final AuthenticationRequest userRegister) throws FieldExistedException {
//        User user = userService.saveUser(userRegister);
//        String message = String.format("User %s register successfully !", user.getUsername());
//        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.CREATED);
//    }


}
