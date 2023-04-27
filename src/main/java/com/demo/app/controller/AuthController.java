package com.demo.app.controller;

import com.demo.app.dto.message.ResponseMessage;
import com.demo.app.dto.user.SignInAndUpDto;
import com.demo.app.exception.UsernameExistException;
import com.demo.app.model.User;
import com.demo.app.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/api/v1/")
@AllArgsConstructor
public class AuthController {

    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    @PostMapping(path = "/auth/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody final SignInAndUpDto userLogin) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userLogin.getUsername(),
                userLogin.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String message = "User signed-in successfully !";
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
    }


    @PostMapping(path = "/auth/signup")
    public ResponseEntity<?> registerUser(@RequestBody final SignInAndUpDto userRegister) {
        try {
            User user = userService.saveUser(userRegister);
            String message = String.format("User %s register successfully !", user.getUsername());
            return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.CREATED);
        } catch (UsernameExistException ex) {
            return new ResponseEntity<>(new ResponseMessage(ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }


}
