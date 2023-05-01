package com.demo.app.service;

import com.demo.app.dto.subject.SubjectRequest;
import com.demo.app.exception.FieldExistedException;

public interface SubjectService {
    void addSubject(SubjectRequest request) throws FieldExistedException;
}
