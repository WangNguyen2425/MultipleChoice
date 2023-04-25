package com.demo.app.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class StudentPageResponse {

    private List<StudentDto> studentDtos;

    private int pageNo;

    private int pageSize;

    private long totalElements;

    private long totalPages;

    private boolean isFirst;

    private boolean isLast;

}
