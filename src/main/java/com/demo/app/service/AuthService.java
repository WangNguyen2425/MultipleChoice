package com.demo.app.service;

import com.demo.app.dto.auth.AuthenticationRequest;
import com.demo.app.dto.auth.AuthenticationResponse;
import com.demo.app.dto.auth.RegisterRequest;
import jakarta.transaction.Transactional;

public interface AuthService {
    @Transactional
    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse login(AuthenticationRequest request);
}
