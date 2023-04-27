package com.demo.app.service;

import com.demo.app.dto.user.SignInAndUpDto;
import com.demo.app.model.User;

public interface UserService {
    User saveUser(SignInAndUpDto requestDto);
}
