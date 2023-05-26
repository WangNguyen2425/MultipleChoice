package com.demo.app.controller;

import com.demo.app.dto.message.ResponseMessage;
import com.demo.app.dto.teacher.TeacherRequest;
import com.demo.app.dto.teacher.TeacherResponse;
import com.demo.app.exception.FieldExistedException;
import com.demo.app.service.TeacherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jdk.jfr.ContentType;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/teacher")
@AllArgsConstructor
@Tag(name = "Teacher")
public class TeacherController {

    private final TeacherService teacherService;


    @Operation(
            summary = "Create a new Teacher",
            description = "Create a new teacher by form is sent",
            method = "POST",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "These are group of information used for teacher register",
                    content = @Content(
                            mediaType = "json/application",
                            schema = @Schema(
                                    description = "This is structure of data teacher register",
                                    implementation = TeacherRequest.class,
                                    example = ""
                            )
                    )
            ),
            responses = @ApiResponse(
                    description = "New teacher is created successfully",
                    responseCode = "200",
                    content = @Content(
                            mediaType = "json/application",
                            schema = @Schema(
                                    implementation = ResponseMessage.class,
                                    example = ""
                            )
                    )
            )
    )
    @PostMapping(path = "/add")
    public ResponseEntity<?> addNewTeacher(@Parameter(
            description = "Information to create new teacher",
            schema = @Schema(
                    implementation = TeacherRequest.class
            ),
            example = ""
    ) @RequestBody @Valid TeacherRequest request) throws FieldExistedException {
        teacherService.saveTeacher(request);
        String message = String.format("Teacher %s have been saved successfully !", request.getFullName());
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.CREATED);

    }

    @Operation(
            summary = "Return all teacher",
            description = "Return all the information of teachers in the database",
            method = "GET",
            responses = {
                    @ApiResponse(
                            description = "Return all information of teachers successfully",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "json/application",
                                    schema = @Schema(
                                            description = "return a list of teacher",
                                            implementation = List.class
                                    ),
                                    examples = @ExampleObject(
                                            value = ""
                                    )
                            )
                    ),
                    @ApiResponse(
                            description = "There is no teacher in database",
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "json/application",
                                    schema = @Schema(
                                            implementation = ResponseMessage.class
                                    ),
                                    examples = @ExampleObject(
                                            value = "Teacher is empty"
                                    )
                            )
                    )
            }
    )
    @GetMapping(path = "/list")
    public ResponseEntity<?> getAllTeachers() {
        var teacherResponses = teacherService.getAllTeacher();
        if (teacherResponses.size()!=0){
            return ResponseEntity.status(HttpStatus.OK).body(teacherResponses);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("Teacher is empty"));
        }
    }


    @Operation(
            summary = "Update teacher",
            description = "Update information for teacher by information is sent",
            method = "PUT",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "json/application",
                            schema = @Schema(
                                    implementation = TeacherRequest.class
                            ),
                            examples = @ExampleObject(
                                    value = ""
                            )
                    )
            ),
            responses = {
                    @ApiResponse(

                    )
            }
    )
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<?> updateTeacher(@Parameter(
            description = "This is ID of teacher need to be updated",
            example = "1"
    ) @PathVariable("id") int teacherId, @RequestBody @Valid TeacherRequest request){
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
