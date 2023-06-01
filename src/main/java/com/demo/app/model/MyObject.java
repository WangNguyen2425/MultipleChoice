package com.demo.app.model;
import com.fasterxml.jackson.annotation.JsonProperty;


public class MyObject {
    @JsonProperty("info")
    private InfoObject info;

    @JsonProperty("answer")
    private AnswerObject answer;

    // Getter và Setter

    public InfoObject getInfo() {
        return info;
    }

    public void setInfo(InfoObject info) {
        this.info = info;
    }

    public AnswerObject getAnswer() {
        return answer;
    }

    public void setAnswer(AnswerObject answer) {
        this.answer = answer;
    }

}


