package com.demo.app.dto.question;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionResponse {

    private int id;

    private String topicText;

    private byte[] topicImage;

    private String level;

    private String createdDate;

}
