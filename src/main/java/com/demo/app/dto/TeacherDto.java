package com.demo.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TeacherDto {

    private String username;

    private String password;

    private String fullName;

    private String birthday;

    private String gender;

    private String email;

    private String phoneNumber;


}
