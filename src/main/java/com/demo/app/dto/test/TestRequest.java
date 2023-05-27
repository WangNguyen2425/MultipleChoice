package com.demo.app.dto.test;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TestRequest {

    private String subjectCode;

    private List<Integer> chapterOrders;

    private int questionQuantity;

}
