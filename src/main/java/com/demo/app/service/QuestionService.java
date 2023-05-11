package com.demo.app.service;


import com.demo.app.dto.question.QuestionRequest;

import java.io.IOException;

public interface QuestionService {


    void addQuestion(int chapterId, QuestionRequest request) throws IOException;
}
