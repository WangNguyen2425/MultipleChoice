package com.demo.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {

    private String username;

    private String password;

    private String fullName;

    private String birthday;

    private String gender;

    private String phoneNumber;

    private String email;

}
