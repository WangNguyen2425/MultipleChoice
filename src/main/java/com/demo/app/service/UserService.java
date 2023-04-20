package com.demo.app.service;

import com.demo.app.dto.UserDto;
import com.demo.app.model.User;

public interface UserService {
    User saveUser(UserDto userDto);
}
