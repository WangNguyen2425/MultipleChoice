package com.demo.app.service;

import com.demo.app.dto.subject.SubjectRequest;
import com.demo.app.dto.subject.SubjectResponse;
import com.demo.app.exception.EntityNotFoundException;
import com.demo.app.exception.FieldExistedException;

import java.util.List;

public interface SubjectService {
    void addSubject(SubjectRequest request) throws FieldExistedException;

    List<SubjectResponse> getAllSubjects() throws EntityNotFoundException;

    void updateSubject(int subjectId, SubjectRequest request) throws EntityNotFoundException;

    void disableSubject(int subjectId);
}
