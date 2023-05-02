package com.demo.app.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AuthenticationRequest {

    @Email(regexp = "[a-z0-9](\\.?[a-z0-9]){5,}@g(oogle)?mail\\.com$", message = "Email is invalid !")
    private String email;

    @NotBlank(message = "Please enter password !")
    private String password;

}
