package com.demo.app.controller;

import com.demo.app.dto.message.ResponseMessage;
import com.demo.app.dto.subject.SubjectRequest;
import com.demo.app.service.SubjectService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/subject")
@AllArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    @PostMapping(path = "/add")
    public ResponseEntity<?> addSubject(@RequestBody @Valid final SubjectRequest request){
        subjectService.addSubject(request);
        String message = "Add subject successfully !";
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.CREATED);
    }
}
