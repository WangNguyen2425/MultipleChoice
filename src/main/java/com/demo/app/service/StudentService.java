package com.demo.app.service;

import com.demo.app.dto.StudentDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StudentService {
    void saveStudentsExcelFile(MultipartFile file) throws IOException;

    void saveStudent(StudentDto studentDto);
}
