package com.demo.app.controller;

import com.demo.app.dto.message.ResponseMessage;
import com.demo.app.dto.page.PageRequest;
import com.demo.app.dto.page.PageResponse;
import com.demo.app.dto.student.StudentRequest;
import com.demo.app.dto.student.StudentResponse;
import com.demo.app.exception.FileInputException;
import com.demo.app.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/student")
@Tag(name = "Student", description = "Student APIs Management")
@AllArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping(path = "/import")
    public ResponseEntity<?> importExcelFile(@RequestBody final MultipartFile file) throws FileInputException {
        studentService.saveStudentsExcelFile(file);
        String message = "Uploaded the file successfully: " + file.getOriginalFilename();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
    }

    @GetMapping(path = "/export")
    public ResponseEntity<?> exportExcelFile() throws IOException {
        String filename = "students.xlsx";
        var file = new InputStreamResource(studentService.exportStudentsExcel());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);
    }

    @Operation(
            summary = "Register a new Student",
            description = "Register a new Student by form submit",
            method = "POST",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "json/application",
                            schema = @Schema(
                                    implementation = StudentRequest.class,
                                    description = "Information's need to be sent to create s new student"
                            )
                    ),
                    required = true
            ),
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "New Student is created successfully",
                    content = @Content(
                            mediaType = "json/application",
                            schema = @Schema(
                                    implementation = ResponseMessage.class
                            )
                    )
            )
    )
    @PostMapping(path = "/add")
    public ResponseEntity<?> addNewStudent(@RequestBody @Valid StudentRequest request) {
        studentService.saveStudent(request);
        String message = String.format("Student %s have been saved successfully !", request.getFullName());
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.CREATED);
    }

    @GetMapping(path = "/page")
    public ResponseEntity<PageResponse<StudentResponse>> getStudentPages(@RequestBody PageRequest request) {
        var response = studentService.getAllStudents(
                request.getPageNo(),
                request.getPageSize(),
                request.getSortBy(),
                request.getSortDir());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(
            summary = "Return all students",
            description = "return all list of all information of students",
            method = "GET",
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "Return all information of students successfully"
            )
    )
    @GetMapping(path = "/list")
    public ResponseEntity<List<StudentResponse>> getAllStudents() {
        var studentResponses = studentService.getAllStudents();
        return ResponseEntity.status(HttpStatus.OK).body(studentResponses);
    }

    @Operation(
            summary = "Update Information For A Student",
            description = "Update information student by id and information are needed to updated in request body",
            method = "PUT",
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "Updated successfully",
                    content = @Content(
                            mediaType = "json/application",
                            examples = @ExampleObject(
                                    description = "there is string announcement is returned to know that the student is updated",
                                    value = "{\"message\":\"Student with id = %d updated successfully !\"}"
                            ),
                            schema = @Schema(
                                    implementation = ResponseMessage.class
                            )
                    )
            )
    )
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<?> updateStudent(
            @Parameter(name = "id",
                    description = "This is the ID student need to be updated",
                    example = "1")
            @PathVariable(name = "id") int studentId,
            @RequestBody StudentRequest request) {
        studentService.updateStudent(studentId, request);
        String message = String.format("Student with id = %d updated successfully !", studentId);
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.OK);
    }

    @Operation(
            summary = "Delete student",
            description = "Delete student in Database",
            method = "DELETE"
    )
    @DeleteMapping(path = "/disable/{id}")
    public ResponseEntity<?> disableStudent(@Parameter(description = "This is ID of student need to be deleted", example = "1")
                                            @PathVariable(name = "id") int studentId) {
        studentService.disableStudent(studentId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable(name = "id") int studentId) {
        studentService.deleteStudent(studentId);
        return ResponseEntity.noContent().build();
    }
}
