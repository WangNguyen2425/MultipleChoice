package com.demo.app.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StudentService {
    void saveStudentsExcelFile(MultipartFile file) throws IOException;
}
