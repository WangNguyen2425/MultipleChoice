package com.demo.app.service.impl;

import com.demo.app.dto.test.TestRequest;
import com.demo.app.model.Question;
import com.demo.app.repository.QuestionRepository;
import com.demo.app.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final QuestionRepository questionRepository;

    public void createTest(TestRequest request){
        var questions = questionRepository.findQuestionBySubjectChapterOrder(request.getSubjectCode(), request.getChapterOrders());

    }
    private List<Question> getRandomQuestionsByLevel(List<Question> questions, int questionQuantity){
        var QUESTION_LEVEL_EASY_RATE = 0.5d;
        var QUESTION_LEVEL_MEDIUM_RATE = 0.3d;
        var questionEasyQuantity = Math.round(questionQuantity * QUESTION_LEVEL_EASY_RATE);
        var temp = (questionQuantity - questionEasyQuantity) * QUESTION_LEVEL_MEDIUM_RATE;
        var questionMediumQuantity = (temp - Math.floor(temp) > 0.5) ? Math.round(temp) : (int) temp;
        var questionDifficultQuantity = questionQuantity - questionEasyQuantity - questionMediumQuantity;

        return null;
    }



}
