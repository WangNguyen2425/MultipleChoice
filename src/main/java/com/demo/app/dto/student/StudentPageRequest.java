package com.demo.app.dto.student;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StudentPageRequest {

    private int pageNo;

    private int pageSize;

    private String sortBy;

    private String sortDir;

}