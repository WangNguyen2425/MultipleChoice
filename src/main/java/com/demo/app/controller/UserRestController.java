package com.demo.app.controller;

import com.demo.app.dto.RegisterDto;
import com.demo.app.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.AllArgsConstructor;
@RestController
@RequestMapping(path = "/api/v1/")
@AllArgsConstructor
public class UserRestController {

    private final UserService userService;

    @PostMapping("/auth/signup")
    public ResponseEntity<?> registerUser(@RequestBody final RegisterDto request){
        if (userService.existsByUsername(request.getUsername())){
            return new ResponseEntity<>("Username already taken !", HttpStatus.BAD_REQUEST);
        }
        userService.saveUser(request);
        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }
}
