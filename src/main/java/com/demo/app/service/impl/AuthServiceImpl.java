package com.demo.app.service.impl;

import com.demo.app.config.security.PasswordEncoder;
import com.demo.app.dto.auth.AuthenticationRequest;
import com.demo.app.dto.auth.AuthenticationResponse;
import com.demo.app.dto.auth.RegisterRequest;
import com.demo.app.dto.user.UserResponse;
import com.demo.app.exception.EntityNotFoundException;
import com.demo.app.model.Role;
import com.demo.app.model.Token;
import com.demo.app.model.User;
import com.demo.app.repository.RoleRepository;
import com.demo.app.repository.TokenRepository;
import com.demo.app.repository.UserRepository;
import com.demo.app.service.AuthService;
import com.demo.app.util.JwtUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager manager;

    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;

    private final JwtUtils jwtUtils;

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final TokenRepository tokenRepository;

    @Override
    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {
        List<Role> roles = Collections.singletonList(roleRepository.findByRoleName(Role.RoleType.ROLE_USER).get());
        User user = mapper.map(request, User.class);
        user.setRoles(roles);
        var savedUser = userRepository.save(user);
        var jwtToken = jwtUtils.generateToken(user);
        var refreshToken = jwtUtils.generateRefreshToken(user);
        Token token = Token.builder()
                .user(savedUser)
                .token(jwtToken)
                .build();
        tokenRepository.save(token);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private String encode(String password) {
        return passwordEncoder.passwordEncode().encode(password);
    }

    @Override
    @Transactional
    public AuthenticationResponse login(AuthenticationRequest request) {
        manager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        ));

        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> {
            throw new EntityNotFoundException("", HttpStatus.NOT_FOUND);
        });
        var jwtToken = jwtUtils.generateToken(user);
        var refreshToken = jwtUtils.generateRefreshToken(user);
        revokeAllUserTokens(user);
        Token token = Token.builder()
                .user(user)
                .token(jwtToken)
                .build();
        tokenRepository.save(token);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();


    }

    private void revokeAllUserTokens(User user){
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty()){
            return;
        }
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
}
