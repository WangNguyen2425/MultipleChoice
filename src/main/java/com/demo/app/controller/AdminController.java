package com.demo.app.controller;

import com.demo.app.dto.*;
import com.demo.app.service.StudentService;
import com.demo.app.service.TeacherService;
import com.demo.app.service.UserService;
import com.demo.app.util.ExcelUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping(path = "/api/v1")
@AllArgsConstructor
public class AdminController {

    private final StudentService studentService;

    private final UserService userService;

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
    public ResponseEntity<?> addNewStudent(@RequestBody StudentDto studentDto) {
        if (userService.existsByUsername(studentDto.getUsername())) {
            String message = "Username already taken !";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
        }
        studentService.saveStudent(studentDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/students/list")
    public ResponseEntity<StudentPageResponse> getAllStudents(@RequestBody StudentPageRequest request) {
        StudentPageResponse response = studentService.getAllStudents(
                request.getPageNo(),
                request.getPageSize(),
                request.getSortBy(),
                request.getSortDir());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping(path = "/teacher/add")
    public ResponseEntity<?> addNewTeacher(@RequestBody TeacherDto teacherDto){
        if (userService.existsByUsername(teacherDto.getUsername())){
            String message = "Username already taken!";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
        }
        teacherService.saveTeacher(teacherDto);
        String message = "Save teacher successfully !";
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
    }

}
