package com.demo.app.controller;

import com.demo.app.dto.ResponseMessage;
import com.demo.app.service.StudentService;
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

    @GetMapping(path = "/students/import")
    public ResponseEntity<?> importExcelFile(@RequestBody MultipartFile file){
        String message;
        if (ExcelUtils.hasExcelFormat(file)){
            try {
                studentService.saveStudentsExcelFile(file);
                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            } catch (IOException e){
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            } catch (Exception e){
                e.printStackTrace();
                message = "Could not read the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
        }
        message = "Please upload an excel file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }


}
