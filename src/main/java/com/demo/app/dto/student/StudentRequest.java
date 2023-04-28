package com.demo.app.dto.student;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudentRequest {

    @NotBlank(message = "Please enter username !")
    private String username;

    @NotBlank(message = "Please enter password !")
    private String password;

    @NotBlank(message = "Please enter your name !")
    private String fullName;

    private String birthday;

    private String gender;

    @Pattern(regexp = "(84|0[3|5789])+([0-9]{8})\\b", message = "Phone number is invalid")
    private String phoneNumber;

    @Email(regexp = "[a-z0-9](\\.?[a-z0-9]){5,}@g(oogle)?mail\\.com$", message = "Email is invalid !")
    private String email;

}
