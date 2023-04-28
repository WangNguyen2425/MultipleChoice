package com.demo.app.controller;

import com.demo.app.dto.message.ResponseMessage;
import com.demo.app.dto.student.StudentPageRequest;
import com.demo.app.dto.student.StudentPageResponse;
import com.demo.app.dto.student.StudentRequest;
import com.demo.app.dto.student.StudentResponse;
import com.demo.app.dto.teacher.TeacherRequest;
import com.demo.app.dto.teacher.TeacherResponse;
import com.demo.app.exception.FieldExistedException;
import com.demo.app.service.StudentService;
import com.demo.app.service.TeacherService;
import com.demo.app.util.ExcelUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping(path = "/api/v1")
@AllArgsConstructor
public class AdminController {
    private final StudentService studentService;
    private final TeacherService teacherService;

    @GetMapping(path = "/students/import")
    public ResponseEntity<?> importExcelFile(@RequestBody MultipartFile file) {
        if (ExcelUtils.hasExcelFormat(file)) {
            try {
                studentService.saveStudentsExcelFile(file);
                String message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            } catch (IOException e) {
                String message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            } catch (Exception e) {
                e.printStackTrace();
                String message = "Could not read the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
        }
        String message = "Please upload an excel file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }

    @PostMapping(path = "/student/add")
    public ResponseEntity<?> addNewStudent(@RequestBody @Valid StudentRequest request) throws FieldExistedException {
        studentService.saveStudent(request);
        String message = String.format("Student %s have been saved successfully !", request.getFullName());
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.CREATED);
    }

    @GetMapping(path = "/students/page")
    public ResponseEntity<StudentPageResponse> getPageStudents(@RequestBody StudentPageRequest request) {
        StudentPageResponse response = studentService.getAllStudents(
                request.getPageNo(),
                request.getPageSize(),
                request.getSortBy(),
                request.getSortDir());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(path = "/students")
    public ResponseEntity<List<StudentResponse>> getAllStudents() {
        List<StudentResponse> studentResponses = studentService.getAllStudents();
        return ResponseEntity.status(HttpStatus.OK).body(studentResponses);
    }

    @PutMapping(path = "/student/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable(name = "id") int studentId, @RequestBody StudentRequest request) {
        studentService.updateStudent(studentId, request);
        String message = String.format("Student with id = %d updated successfully !", studentId);
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.OK);
    }

    @PostMapping(path = "/teacher/add")
    public ResponseEntity<?> addNewTeacher(@RequestBody TeacherRequest request) throws FieldExistedException {
        teacherService.saveTeacher(request);
        String message = String.format("Teacher %s have been saved successfully !", request.getFullName());
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.CREATED);

    }

    @GetMapping(path = "/teachers")
    public ResponseEntity<List<TeacherResponse>> getAllTeachers() {
        List<TeacherResponse> teacherResponses = teacherService.getAllTeacher();
        return ResponseEntity.status(HttpStatus.OK).body(teacherResponses);
    }

}