package com.demo.app.service.impl;

import com.demo.app.config.security.PasswordEncoder;
import com.demo.app.dto.auth.AuthenticationRequest;
import com.demo.app.exception.FieldExistedException;
import com.demo.app.model.Role;
import com.demo.app.model.User;
import com.demo.app.repository.RoleRepository;
import com.demo.app.repository.UserRepository;
import com.demo.app.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper;

    private static final String USER_NOT_FOUND_MSG = "User with username: %s not found !";

    @Override
    public User saveUser(AuthenticationRequest request) throws FieldExistedException {
        if (userRepository.existsByUsername(request.getUsername())){
            throw new FieldExistedException("Username already taken !", HttpStatus.BAD_REQUEST);
        }
        User user = modelMapper.map(request, User.class);

        user.setPassword(passwordEncoder.passwordEncode().encode(request.getPassword()));

        Role role = roleRepository.findByRoleName(Role.RoleType.ROLE_USER).get();
        user.setRoles(Collections.singletonList(role));
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, username)));
    }
}
