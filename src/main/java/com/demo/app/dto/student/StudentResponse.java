package com.demo.app.dto.student;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponse {

    private int id;

    private String username;

    private String fullName;

    private String birthday;

    private String Gender;

    private String phoneNumber;

    private String email;

}