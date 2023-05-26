package com.demo.app.dto.page;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PageRequest {

    private int pageNo = 0;

    private int pageSize = 10;

    private String sortBy = "id";

    private String sortDir = "asc";

}
