package com.demo.app.service.impl;


import com.demo.app.dto.question.QuestionRequest;
import com.demo.app.exception.EntityNotFoundException;
import com.demo.app.model.Question;
import com.demo.app.repository.ChapterRepository;
import com.demo.app.repository.QuestionRepository;
import com.demo.app.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    private final ChapterRepository chapterRepository;

    private final ModelMapper mapper;

    @Override
    public void addQuestion(int chapterId, QuestionRequest request) throws IOException, EntityNotFoundException {
        var chapter = chapterRepository.findById(chapterId).orElseThrow(() -> {
            throw new EntityNotFoundException(String.format("Not found any chapter with id: %d !", chapterId), HttpStatus.NOT_FOUND);
        });
        var question = mapper.map(request, Question.class);
        question.setChapter(chapter);
        if (request.getTopicImageFile() != null) {
            byte[] image = request.getTopicImageFile().getBytes();
            question.setTopicImage(image);
        }
        questionRepository.save(question);
    }


}
