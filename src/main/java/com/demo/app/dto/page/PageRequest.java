package com.demo.app.dto.page;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PageRequest {

    private int pageNo;

    private int pageSize;

    private String sortBy;

    private String sortDir;

}
