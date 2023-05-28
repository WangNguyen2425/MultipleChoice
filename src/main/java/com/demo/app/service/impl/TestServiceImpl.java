package com.demo.app.service.impl;

import com.demo.app.dto.answer.AnswerResponse;
import com.demo.app.dto.question.QuestionResponse;
import com.demo.app.dto.test.TestRequest;
import com.demo.app.dto.test.TestResponse;
import com.demo.app.exception.EntityNotFoundException;
import com.demo.app.repository.QuestionRepository;
import com.demo.app.repository.SubjectRepository;
import com.demo.app.service.TestService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final QuestionRepository questionRepository;

    private final SubjectRepository subjectRepository;

    private final ModelMapper mapper;

    @Override
    @Transactional
    public TestResponse createTestFirstStep(TestRequest request) throws EntityNotFoundException {
        var subject = subjectRepository.findByCode(request.getSubjectCode()).orElseThrow(
                () -> new EntityNotFoundException(String.format("Code: %s not found !", request.getSubjectCode()), HttpStatus.NOT_FOUND)
        );
        final var FIRST_RESULTS = 0;
        var pageable = PageRequest.of(FIRST_RESULTS, request.getQuestionQuantity());
        var questions = questionRepository.findQuestionBySubjectChapterOrder(request.getSubjectCode(), request.getChapterOrders(), pageable);
        var questionResponses = questions.stream().map(question -> {
            var response = mapper.map(question, QuestionResponse.class);
            question.getAnswers().forEach(answer -> response.getAnswers().add(mapper.map(answer, AnswerResponse.class)));
            return response;
        }).collect(Collectors.toList());
        return TestResponse.builder()
                .testDay(request.getTestDay())
                .subjectCode(subject.getCode())
                .subjectTitle(subject.getTitle())
                .questionResponses(questionResponses)
                .build();
    }


}
