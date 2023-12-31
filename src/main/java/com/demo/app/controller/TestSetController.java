package com.demo.app.controller;

import com.demo.app.dto.message.ResponseMessage;
import com.demo.app.dto.testset.TestSetRequest;
import com.demo.app.service.TestSetService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/test-set")
@Tag(name = "Test-Set", description = "TestSet API management")
@RequiredArgsConstructor
public class TestSetController {

    private final TestSetService testSetService;

    @PostMapping(path = "/test-set/{test-id}/create")
    public ResponseEntity<?> createTestSetFromTest(@PathVariable(name = "test-id") int testId,
                                                   @RequestBody @Valid final TestSetRequest request) throws InterruptedException {
        testSetService.createTestSetFromTest(testId, request);
        Thread.sleep(5000);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseMessage(String.format("Created Set of test with id %d successfully !", testId)));
    }

    @GetMapping(path = "/test-set/list")
    public ResponseEntity<?> getAllTestSet(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(testSetService.getAllTestSet());
    }

    @GetMapping(path = "/test-set/detail/{id}")
    public ResponseEntity<?> getTestSetDetail(@PathVariable(name = "id") int testSetId){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(testSetService.getTestSetDetail(testSetId));
    }
}
