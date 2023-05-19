package com.demo.app.service;

import com.demo.app.dto.page.PageResponse;
import com.demo.app.dto.student.StudentRequest;
import com.demo.app.dto.student.StudentResponse;
import com.demo.app.exception.EntityNotFoundException;
import com.demo.app.exception.FieldExistedException;
import com.demo.app.exception.FileInputException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StudentService {

    void saveStudentsExcelFile(MultipartFile file) throws FileInputException, FieldExistedException;

    void saveStudent(StudentRequest request) throws FieldExistedException;

    PageResponse<StudentResponse> getAllStudents(int pageNo, int pageSize, String sortBy, String sortDir);

    List<StudentResponse> getAllStudents();

    void updateStudent(int studentId, StudentRequest request) throws EntityNotFoundException;

    void disableStudent(int studentId) throws EntityNotFoundException;

    void deleteStudent(int studentId) throws EntityNotFoundException;
}
