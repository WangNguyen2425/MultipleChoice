package com.demo.app.service.impl;

import com.demo.app.dto.auth.AuthenticationRequest;
import com.demo.app.dto.auth.AuthenticationResponse;
import com.demo.app.dto.user.UserResponse;
import com.demo.app.model.User;
import com.demo.app.service.AuthService;
import com.demo.app.util.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager manager;

    private final JwtUtils jwtUtils;


    @Override
    public AuthenticationResponse login(AuthenticationRequest request){
        Authentication authentication = manager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User principal = (User) authentication.getPrincipal();
        var userResponse = new UserResponse(principal.getUsername(), principal.isStatus());
        String token = jwtUtils.generateJwtToken(authentication);
        return new AuthenticationResponse(userResponse, token);
    }
}
