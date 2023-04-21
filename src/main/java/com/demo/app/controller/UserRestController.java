package com.demo.app.controller;

import com.demo.app.dto.UserDto;
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
public class UserRestController {

    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    @PostMapping(path = "/auth/signin")
    public ResponseEntity<String> authenticateUser(@RequestBody final UserDto userLogin) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userLogin.getUsername(),
                userLogin.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("User signed-in successfully !", HttpStatus.OK);
    }


    @PostMapping(path = "/auth/signup")
    public ResponseEntity<?> registerUser(@RequestBody final UserDto userRegister) {
        if (userService.existsByUsername(userRegister.getUsername())) {
            return new ResponseEntity<>("Username already taken !", HttpStatus.BAD_REQUEST);
        }
        User user = userService.saveUser(userRegister);
        return new ResponseEntity<>("User " + user.getUsername() + " registered successfully", HttpStatus.OK);
    }


}
