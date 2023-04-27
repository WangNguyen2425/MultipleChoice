package com.demo.app.dto.user;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SignInAndUpDto {

    private String username;

    private String password;

}
