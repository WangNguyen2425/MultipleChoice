package com.demo.app.dto.student;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentPageResponse {

    private List<StudentResponse> studentDtos;

    private int pageNo;

    private int pageSize;

    private long totalElements;

    private long totalPages;

    private boolean isFirst;

    private boolean isLast;

}
