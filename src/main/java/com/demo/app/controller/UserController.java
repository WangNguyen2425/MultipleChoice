package com.demo.app.controller;

import com.demo.app.dto.UserDto;
import com.demo.app.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public String userSignUp(@RequestBody UserDto request){
        return "";
    }
}
