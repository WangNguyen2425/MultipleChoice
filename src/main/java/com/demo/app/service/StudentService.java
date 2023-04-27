package com.demo.app.service;

import com.demo.app.dto.student.StudentRequest;
import com.demo.app.dto.student.StudentPageResponse;
import com.demo.app.dto.student.StudentResponse;
import com.demo.app.exception.StudentNotFoundException;
import com.demo.app.exception.UsernameExistException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface StudentService {
    void saveStudentsExcelFile(MultipartFile file) throws IOException;

    void saveStudent(StudentRequest request) throws UsernameExistException;

    StudentPageResponse getAllStudents(int pageNo, int pageSize, String sortBy, String sortDir);

    List<StudentResponse> getAllStudents();

    void updateStudent(int studentId, StudentRequest request) throws StudentNotFoundException;
}
