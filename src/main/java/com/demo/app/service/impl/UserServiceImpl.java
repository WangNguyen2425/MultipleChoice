package com.demo.app.service.impl;

import com.demo.app.config.security.PasswordEncoder;
import com.demo.app.dto.SignInAndUpDto;
import com.demo.app.model.Role;
import com.demo.app.model.User;
import com.demo.app.repository.RoleRepository;
import com.demo.app.repository.UserRepository;
import com.demo.app.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper;

    private static final String USER_NOT_FOUND_MSG = "User with username: %s not found !";

    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public User saveUser(SignInAndUpDto requestDto) {
        User user = modelMapper.map(requestDto, User.class);
        user.setPassword(passwordEncoder.passwordEncode().encode(requestDto.getPassword()));

        Role role = roleRepository.findByRoleName("ROLE_USER").get();
        user.setRoles(Collections.singletonList(role));
        return userRepository.save(user);
    }

    public Optional<User> findById(Integer integer) {
        return userRepository.findById(integer);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, username)));
    }
}
