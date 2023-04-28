package com.demo.app.dto.user;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SignInAndUpDto {

    @NotBlank(message = "Please enter username !")
    private String username;

    @NotBlank(message = "Please enter password !")
    private String password;

}
