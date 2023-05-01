package com.demo.app.controller;

import com.demo.app.dto.message.ResponseMessage;
import com.demo.app.dto.teacher.TeacherRequest;
import com.demo.app.dto.teacher.TeacherResponse;
import com.demo.app.exception.FieldExistedException;
import com.demo.app.service.TeacherService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/teacher")
@AllArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @PostMapping(path = "/add")
    public ResponseEntity<?> addNewTeacher(@RequestBody TeacherRequest request) throws FieldExistedException {
        teacherService.saveTeacher(request);
        String message = String.format("Teacher %s have been saved successfully !", request.getFullName());
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.CREATED);

    }

    @GetMapping(path = "/list")
    public ResponseEntity<List<TeacherResponse>> getAllTeachers() {
        List<TeacherResponse> teacherResponses = teacherService.getAllTeacher();
        return ResponseEntity.status(HttpStatus.OK).body(teacherResponses);
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<?> updateTeacher(@PathVariable("id") int teacherId, @RequestBody TeacherRequest request){
        teacherService.updateTeacher(teacherId, request);
        String message = String.format("Teacher with id = %d updated successfully !", teacherId);
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.OK);
    }

    @DeleteMapping(path = "/disable/{id}")
    public ResponseEntity<?> disableTeacher(@PathVariable("id") int teacherId){
        teacherService.disableTeacher(teacherId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<?> deleteTeacher(@PathVariable("id") int teacherId){
        teacherService.deleteTeacher(teacherId);
        return ResponseEntity.noContent().build();
    }
}
