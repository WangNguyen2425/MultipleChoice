package com.demo.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/test")
public class TestController {

    @PostMapping(path = "/create")
    public ResponseEntity<?> createTest(){
        return null;
    }



}
