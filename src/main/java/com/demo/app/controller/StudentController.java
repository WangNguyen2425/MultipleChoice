package com.demo.app.controller;

import com.demo.app.dto.message.ResponseMessage;
import com.demo.app.dto.student.StudentPageRequest;
import com.demo.app.dto.student.StudentPageResponse;
import com.demo.app.dto.student.StudentRequest;
import com.demo.app.dto.student.StudentResponse;
import com.demo.app.exception.FieldExistedException;
import com.demo.app.exception.FileInputException;
import com.demo.app.service.StudentService;
import com.demo.app.util.ExcelUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/student")
@AllArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping(path = "/import")
    public ResponseEntity<?> importExcelFile(@RequestBody final MultipartFile file) throws FileInputException {
        if (!ExcelUtils.hasExcelFormat(file)) {
            String message = "Please upload an excel file!";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
        }
        studentService.saveStudentsExcelFile(file);
        String message = "Uploaded the file successfully: " + file.getOriginalFilename();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
    }

    @PostMapping(path = "/add")
    public ResponseEntity<?> addNewStudent(@RequestBody @Valid StudentRequest request) throws FieldExistedException {
        studentService.saveStudent(request);
        String message = String.format("Student %s have been saved successfully !", request.getFullName());
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.CREATED);
    }

    @GetMapping(path = "/page")
    public ResponseEntity<StudentPageResponse> getPageStudents(@RequestBody StudentPageRequest request) {
        StudentPageResponse response = studentService.getAllStudents(
                request.getPageNo(),
                request.getPageSize(),
                request.getSortBy(),
                request.getSortDir());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(path = "/list")
    public ResponseEntity<List<StudentResponse>> getAllStudents() {
        List<StudentResponse> studentResponses = studentService.getAllStudents();
        return ResponseEntity.status(HttpStatus.OK).body(studentResponses);
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable(name = "id") int studentId, @RequestBody StudentRequest request) {
        studentService.updateStudent(studentId, request);
        String message = String.format("Student with id = %d updated successfully !", studentId);
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.OK);
    }

    @DeleteMapping(path = "/disable/{id}")
    public ResponseEntity<?> disableStudent(@PathVariable(name = "id") int studentId) {
        studentService.disableStudent(studentId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable(name = "id") int studentId) {
        studentService.deleteStudent(studentId);
        return ResponseEntity.noContent().build();
    }
}
