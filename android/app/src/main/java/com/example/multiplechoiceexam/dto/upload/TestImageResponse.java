package com.example.multiplechoiceexam.dto.upload;

import java.util.Date;
import java.util.List;

public class TestImageResponse {

    private ResponseMessage responseMessage;

    private String path;
    private List<Filename> imageFilenames;

    public TestImageResponse() {
    }

    public TestImageResponse(ResponseMessage responseMessage, String path, List<Filename> imageFilenames) {
        this.responseMessage = responseMessage;
        this.path = path;
        this.imageFilenames = imageFilenames;
    }

    public ResponseMessage getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(ResponseMessage responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<Filename> getImageFilenames() {
        return imageFilenames;
    }

    public void setImageFilenames(List<Filename> imageFilenames) {
        this.imageFilenames = imageFilenames;
    }
}
