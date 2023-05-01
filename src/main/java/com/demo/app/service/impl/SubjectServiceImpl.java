package com.demo.app.service.impl;

import com.demo.app.dto.subject.SubjectRequest;
import com.demo.app.exception.FieldExistedException;
import com.demo.app.model.Subject;
import com.demo.app.repository.SubjectRepository;
import com.demo.app.service.SubjectService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final ModelMapper modelMapper;

    private final SubjectRepository subjectRepository;

    @Override
    public void addSubject(SubjectRequest request) throws FieldExistedException{
        if(subjectRepository.existsByCode(request.getCode())){
            throw new FieldExistedException("Subject's code already taken !", HttpStatus.BAD_REQUEST);
        }
        Subject subject = modelMapper.map(request, Subject.class);
        subjectRepository.save(subject);
    }
}
