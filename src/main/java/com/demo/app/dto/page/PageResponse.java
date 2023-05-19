package com.demo.app.dto.page;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageResponse<T> {

    private List<T> objects;

    private int pageNo;

    private int pageSize;

    private long totalElements;

    private long totalPages;

    private boolean isFirst;

    private boolean isLast;
}
