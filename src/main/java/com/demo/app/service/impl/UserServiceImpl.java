package com.demo.app.service.impl;

import com.demo.app.config.PasswordEncoder;
import com.demo.app.dto.UserDto;
import com.demo.app.model.User;
import com.demo.app.repository.UserRepository;
import com.demo.app.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    private static final String USER_NOT_FOUND_MSG = "User with username: %s not found !";

    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User saveUser(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        user.setPassword(passwordEncoder.passwordEncode().encode(userDto.getPassword()));

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
