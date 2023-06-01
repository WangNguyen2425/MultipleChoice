package com.demo.app.controller;

import com.demo.app.dto.message.ResponseMessage;
import com.demo.app.dto.test.TestRequest;
import com.demo.app.dto.test.TestDetailResponse;
import com.demo.app.dto.testset.TestSetRequest;
import com.demo.app.exception.EntityNotFoundException;
import com.demo.app.model.AnswerObject;
import com.demo.app.model.InfoObject;
import com.demo.app.model.MyObject;
import com.demo.app.service.TestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.io.FileReader;
import java.nio.file.Files;

import java.nio.file.Paths;

import java.io.BufferedReader;
import java.io.InputStreamReader;

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

        // Tạo một đối tượng ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
            try {
                // Đọc nội dung file JSON thành một chuỗi
                String jsonContent = new String(Files.readAllBytes(Paths.get(filePath)));

                // Tạo đối tượng MyObject từ chuỗi JSON
                MyObject myObject = new MyObject(jsonContent);

                // Thao tác với đối tượng myObject
                InfoObject info = myObject.getInfo();
                AnswerObject answer = myObject.getAnswer();

                // Truy cập các trường trong info và answer
                // ...
            } catch (IOException e) {
                e.printStackTrace();
            }



        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage("ok"));
    }

}
