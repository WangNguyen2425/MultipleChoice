package com.demo.app.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/student-test")
@RequiredArgsConstructor
@Tag(name = "Student-Test", description = "Manage Student's Test and Marking")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class StudentTestController {

    @PostMapping(path = "/uploads",
            consumes = {MediaType.APPLICATION_JSON_VALUE,
                    MediaType.MULTIPART_FORM_DATA_VALUE}
    )
    public ResponseEntity<?> uploadStudentTestImages(@RequestParam(name = "exam-class") String classCode,
                                                     @RequestPart(name = "files") List<MultipartFile> files){
        return null;
    }



}
