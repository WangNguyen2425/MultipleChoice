package com.demo.app.dto.teacher;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TeacherRequest {

    private String username;

    private String email;

    private String password;

    private String fullName;

    private String birthday;

    private String gender;

    private String phoneNumber;



}
