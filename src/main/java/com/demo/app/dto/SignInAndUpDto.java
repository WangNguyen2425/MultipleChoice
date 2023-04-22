package com.demo.app.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class SignInAndUpDto {

    private String username;

    private String password;

}
