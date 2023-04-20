package com.demo.app.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class UserDto {

    private final String username;

    private final String password;

}
