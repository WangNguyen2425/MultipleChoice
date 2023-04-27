package com.demo.app.service;

import com.demo.app.dto.teacher.TeacherRequest;
import com.demo.app.dto.teacher.TeacherResponse;
import com.demo.app.exception.UsernameExistException;

import java.util.List;

public interface TeacherService {
    void saveTeacher(TeacherRequest request) throws UsernameExistException;

    List<TeacherResponse> getAllTeacher();
}
