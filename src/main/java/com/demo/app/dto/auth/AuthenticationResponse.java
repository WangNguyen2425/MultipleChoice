package com.demo.app.dto.auth;

import com.demo.app.dto.user.UserResponse;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class AuthenticationResponse {

    private UserResponse userResponse;

    private String token;


}
