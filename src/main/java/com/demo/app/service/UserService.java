package com.demo.app.service;

import com.demo.app.dto.RegisterDto;
import com.demo.app.model.User;

public interface UserService {
    Boolean existsByUsername(String username);

    User saveUser(RegisterDto userDto);
}
