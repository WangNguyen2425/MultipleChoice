package com.demo.app.controller;

import com.demo.app.dto.message.ResponseMessage;
import com.demo.app.dto.page.PageRequest;
import com.demo.app.dto.question.QuestionRequest;
import com.demo.app.exception.FileInputException;
import com.demo.app.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(path = "/api/v1")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping(path = "/{chapterId}/question/add",
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.MULTIPART_FORM_DATA_VALUE
            })
    public ResponseEntity<?> addQuestion(@PathVariable("chapterId") int chapterId,
                                         @RequestPart(name = "topicText") String topicText,
                                         @RequestPart(name = "topicImageFile") MultipartFile topicImageFile,
                                         @RequestPart(name = "level") String level) throws FileInputException {
        var request = QuestionRequest.builder()
                .topicText(topicText)
                .topicImageFile(topicImageFile)
                .level(level)
                .build();
        try {
            questionService.addQuestion(chapterId, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage("Add question successfully !"));
        } catch (IOException ex) {
            throw new FileInputException("Could not upload image !", HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping(path = "/{code}/question/list")
    public ResponseEntity<?> getAllQuestionsBySubject(@PathVariable(name = "code") String code){

        return null;
    }

    @GetMapping(path = "/{code}/question/page")
    public ResponseEntity<?> getQuestionPagesBySubject(@PathVariable(name = "code") String code, @RequestBody PageRequest request){
        return null;
    }

}
