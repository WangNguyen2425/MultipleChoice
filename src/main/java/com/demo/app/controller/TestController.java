package com.demo.app.controller;

import com.demo.app.dto.message.ResponseMessage;
import com.demo.app.dto.test.TestRequest;
import com.demo.app.dto.test.TestDetailResponse;
import com.demo.app.exception.EntityNotFoundException;
import com.demo.app.service.TestService;
import jakarta.validation.Valid;
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
    public ResponseEntity<?> createTest(@RequestBody @Valid final TestRequest request) throws EntityNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(testService.createTestFirstStep(request));
    }

    @PostMapping(path = "/create/second-step")
    public ResponseEntity<?> saveTest(@RequestBody final TestDetailResponse response) {
        testService.createTestSecondStep(response);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage("Created test successfully !"));
    }

    @GetMapping(path = "/list")
    public ResponseEntity<?> getAllTests(){
        return ResponseEntity.status(HttpStatus.OK).body(testService.getAllTests());
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<?> updateTest(@PathVariable(name = "id") int testId){
        return null;
    }

    @PostMapping(path = "/test-set/create/{id}")
    public ResponseEntity<?> createTestSetFromTest(@PathVariable(name = "id") int testId){
        return null;
    }


}
