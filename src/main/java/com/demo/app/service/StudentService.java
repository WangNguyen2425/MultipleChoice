package com.demo.app.service;

import com.demo.app.dto.student.StudentRequest;
import com.demo.app.dto.student.StudentPageResponse;
import com.demo.app.dto.student.StudentResponse;
import com.demo.app.exception.EntityNotFoundException;
import com.demo.app.exception.FieldExistedException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface StudentService {
    void saveStudentsExcelFile(MultipartFile file) throws IOException, FieldExistedException;

    void saveStudent(StudentRequest request) throws FieldExistedException;

    StudentPageResponse getAllStudents(int pageNo, int pageSize, String sortBy, String sortDir);

    List<StudentResponse> getAllStudents();

    void updateStudent(int studentId, StudentRequest request) throws EntityNotFoundException;

    void disableStudent(int studentId) throws EntityNotFoundException;

    void deleteStudent(int studentId) throws EntityNotFoundException;
}
