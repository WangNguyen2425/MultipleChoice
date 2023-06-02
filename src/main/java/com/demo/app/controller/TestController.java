package com.demo.app.controller;

import com.demo.app.dto.message.ResponseMessage;
import com.demo.app.dto.test.TestRequest;
import com.demo.app.dto.test.TestDetailResponse;
import com.demo.app.dto.testset.TestSetRequest;
import com.demo.app.exception.EntityNotFoundException;
import com.demo.app.model.MyObject;
import com.demo.app.service.TestService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;

import java.nio.file.Paths;
import java.util.Scanner;

@RestController
@RequestMapping(path = "/api/v1/test")
@RequiredArgsConstructor
@CrossOrigin(allowedHeaders = "*", origins = "*")
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
    public ResponseEntity<?> createTestSetFromTest(@PathVariable(name = "id") int testId, @RequestBody @Valid final TestSetRequest request){
        testService.createTestSetFromTest(testId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage(String.format("Created Set of test with id %d successfully !", testId)));
    }

    @GetMapping(path="/chamdiem")

    public ResponseEntity<?> chamdiem(){
        String filePath = "data.json";
        ObjectMapper objectMapper = new ObjectMapper();
        MyObject myObject = new MyObject();
        try {
            String CMD =
                    "cmd /c python app.py %s";
            CMD = String.format(CMD, "f13.jpg");
            Process process = Runtime.getRuntime().exec(CMD);
//            int exitCode = process.waitFor();
//            if (exitCode == 0) {
//                File file = new File(filePath);
//                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//                objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
//                objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);
//                myObject = objectMapper.readValue(file, MyObject.class);
////                file.delete();
//                return ResponseEntity.status(HttpStatus.CREATED).body(myObject);
//            } else {
//                System.out.println("Quá trình chưa hoàn thành. Mã thoát: " + exitCode);
//            }
//
//            return ResponseEntity.status(HttpStatus.CREATED).body(myObject);


        } catch (Exception e) {
            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage("error"));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage("error"));
    }

}
