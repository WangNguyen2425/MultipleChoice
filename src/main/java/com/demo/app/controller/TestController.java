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

    @PostMapping(path = "/test-set/create/{id}")
    public ResponseEntity<?> createTestSetFromTest(@PathVariable(name = "id") int testId, @RequestBody @Valid final TestSetRequest request){
        testService.createTestSetFromTest(testId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage(String.format("Created Set of test with id %d successfully !", testId)));
    }


    @GetMapping(path="/chamdiem")

    public ResponseEntity<?> chamdiem(){

        try {
            String CMD =
                    "cmd /c python app.py %s";
            CMD = String.format(CMD, "f13.jpg");
            String filePath = "data.json";
            ObjectMapper objectMapper = new ObjectMapper();
            MyObject myObject;
            Process process = Runtime.getRuntime().exec(CMD);
            File file = new File(filePath);
            myObject = objectMapper.readValue(file, MyObject.class);
//            process.destroy();
            return ResponseEntity.status(HttpStatus.CREATED).body(myObject);
//            int exitCode = process.waitFor();
//            if (exitCode == 0) {
//                File file = new File(filePath);
//                myObject = objectMapper.readValue(file, MyObject.class);
//                return ResponseEntity.status(HttpStatus.CREATED).body(myObject);
//            } else {
//                System.out.println("Quá trình chưa hoàn thành. Mã thoát: " + exitCode);
//            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage("error"));
        }
    }

}
