package com.demo.app.service.impl;

import com.demo.app.model.User;
import com.demo.app.repository.StudentRepository;
import com.demo.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (!optionalUser.isEmpty()) {
            User user = optionalUser.get();
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            UserDetails userDetail = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
            return userDetail;
        } else {
            throw new UsernameNotFoundException("Login failed !");
        }
    }
}
