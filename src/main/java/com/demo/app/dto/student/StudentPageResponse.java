package com.demo.app.dto.student;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class StudentPageResponse {

    private List<StudentResponse> studentDtos;

    private int pageNo;

    private int pageSize;

    private long totalElements;

    private long totalPages;

    private boolean isFirst;

    private boolean isLast;

}
