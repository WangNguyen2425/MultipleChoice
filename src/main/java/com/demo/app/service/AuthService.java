package com.demo.app.service;

import com.demo.app.dto.auth.AuthenticationRequest;
import com.demo.app.dto.auth.AuthenticationResponse;

public interface AuthService {
    AuthenticationResponse login(AuthenticationRequest request);
}
