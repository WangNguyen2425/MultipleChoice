package com.demo.app.dto.subject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SubjectResponse {

    private int id;

    private String title;

    private String code;

    private String description;

    private int credit;

}
