package com.demo.app.controller;

import com.demo.app.dto.UserDto;
import com.demo.app.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.AllArgsConstructor;
@RestController
@RequestMapping(path = "api/v1/")
@AllArgsConstructor
public class UserRestController {

    private final UserService userService;

    @PostMapping("/auth/signup")
    public ResponseEntity<?> userSignUp(@RequestBody UserDto request){
        userService.saveUser(request);
        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
