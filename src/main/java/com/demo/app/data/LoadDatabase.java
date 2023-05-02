package com.demo.app.data;

import com.demo.app.config.security.PasswordEncoder;
import com.demo.app.model.Role;
import com.demo.app.model.Token;
import com.demo.app.model.User;
import com.demo.app.repository.RoleRepository;
import com.demo.app.repository.TokenRepository;
import com.demo.app.repository.UserRepository;
import com.demo.app.util.JwtUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LoadDatabase implements CommandLineRunner {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final TokenRepository tokenRepository;

    private final JwtUtils jwtUtils;

    @Override
    @Transactional
    public void run(String... args) {
        initializeRoles();
        initializeAdminUser();
    }
    private void initializeRoles(){
        long roleCount = roleRepository.count();
        if (roleCount == 0) {
            List<Role> roles = new ArrayList<>();
            for (Role.RoleType type : Role.RoleType.values()){
                roles.add(new Role(type));
            }
            roleRepository.saveAll(roles);
        }
    }
    private void initializeAdminUser(){
        if (!userRepository.existsByUsername("admin")) {
            List<Role> roles = roleRepository.findAll();
            User user = User.builder()
                    .username("admin")
                    .email("admin123@gmail.com")
                    .password(passwordEncoder.passwordEncode().encode("admin"))
                    .status(true)
                    .roles(roles)
                    .build();
            userRepository.save(user);
            var savedUser = userRepository.save(user);
            var jwtToken = jwtUtils.generateToken(user);
            Token token = Token.builder()
                    .user(savedUser)
                    .token(jwtToken)
                    .build();
            tokenRepository.save(token);
        }
    }
}
