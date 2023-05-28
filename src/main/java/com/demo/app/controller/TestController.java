package com.demo.app.controller;

import com.demo.app.dto.test.TestRequest;
import com.demo.app.exception.EntityNotFoundException;
import com.demo.app.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/test")
@RequiredArgsConstructor
@CrossOrigin(allowedHeaders = "*")
public class TestController {

    private final TestService testService;

    @PostMapping(path = "/create/first-step")
    public ResponseEntity<?> createTest(@RequestBody TestRequest testRequest) throws EntityNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(testService.createTestFirstStep(testRequest));
    }



}
