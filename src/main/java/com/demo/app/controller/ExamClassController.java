package com.demo.app.controller;

import com.demo.app.dto.examClass.ClassRequest;
import com.demo.app.dto.message.ResponseMessage;
import com.demo.app.exception.InvalidRoleException;
import com.demo.app.service.ExamClassService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(path = "/api/v1/class")
@Tag(name = "Exam-Class")
@RequiredArgsConstructor
public class ExamClassController {

    private final ExamClassService examClassService;

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('TEACHER')")
    public ResponseEntity<?> createExamClass(@RequestBody ClassRequest request, Principal principal){
        if (principal == null){
            throw new InvalidRoleException("You're not logged in !", HttpStatus.UNAUTHORIZED);
        }
        examClassService.createExamClass(request, principal);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseMessage("Create Exam Class successfully !"));
    }

    @PostMapping("/join")
    @PreAuthorize("hasAnyRole('STUDENT')")
    public ResponseEntity<?> joinExamClassByCode(@RequestParam String classCode, Principal principal){
        if (principal == null){
            throw new InvalidRoleException("You are not logged in", HttpStatus.UNAUTHORIZED);
        }
        var examClass = examClassService.joinExamClassByCode(classCode, principal);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseMessage(String.format("Join class %s successfully !", examClass.getRoomName())));
    }

    @GetMapping(path = "/list")
    public ResponseEntity<?> getAllExamClass(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(examClassService.getAllEnabledExamClass());
    }

    @DeleteMapping(path = "/disable/{id}")
    public ResponseEntity<?> disableExamClass(@PathVariable(name = "id") int examClassId){
        examClassService.disableExamClass(examClassId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(new ResponseMessage("Disabled exam class successfully !"));
    }


}
