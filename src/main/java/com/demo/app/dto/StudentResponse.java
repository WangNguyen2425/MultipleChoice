package com.demo.app.dto;


import com.demo.app.model.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponse {

    private String username;

    private String fullName;

    private LocalDate birthday;

    private Gender gender;

    private LocalDate joinDate;

    private String phoneNumber;

    private String email;

    private int pageNo;

    private int pageSize;

    private long totalElement;

    private boolean last;

}
