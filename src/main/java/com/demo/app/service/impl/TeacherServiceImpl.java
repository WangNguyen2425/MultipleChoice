package com.demo.app.service.impl;

import com.demo.app.config.security.PasswordEncoder;
import com.demo.app.dto.teacher.TeacherRequest;
import com.demo.app.dto.teacher.TeacherResponse;
import com.demo.app.exception.EntityNotFoundException;
import com.demo.app.exception.FieldExistedException;
import com.demo.app.model.Role;
import com.demo.app.model.Teacher;
import com.demo.app.model.User;
import com.demo.app.repository.RoleRepository;
import com.demo.app.repository.TeacherRepository;
import com.demo.app.repository.UserRepository;
import com.demo.app.service.TeacherService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public void saveTeacher(TeacherRequest request) throws FieldExistedException {
        checkIfUsernameExists(request.getUsername());
        checkIfEmailExists(request.getEmail());
        checkIfPhoneNumberExists(request.getPhoneNumber());

        List<Role> roles = roleRepository.findAllByRoleNameIn(Arrays.asList(Role.RoleType.ROLE_USER, Role.RoleType.ROLE_TEACHER));

        User user = modelMapper.map(request, User.class);
        String encodePassword = passwordEncoder.passwordEncode().encode(request.getPassword());
        user.setPassword(encodePassword);
        user.setRoles(roles);
        user.getTeacher().setUser(user);
        userRepository.save(user);
    }

    @Override
    public List<TeacherResponse> getAllTeacher(){
        List<Teacher> teachers = teacherRepository.findByStatus(true);
        if (teachers.size() == 0) {
            throw new EntityNotFoundException("Not found any students", HttpStatus.NOT_FOUND);
        }
        return teachers.stream().map(teacher -> {
            TeacherResponse response = modelMapper.map(teacher, TeacherResponse.class);
            response.setUsername(teacher.getUser().getUsername());
            return response;
        }).collect(Collectors.toList());
    }
    private void checkIfUsernameExists(String username) throws FieldExistedException {
        if (userRepository.existsByUsername(username)) {
            throw new FieldExistedException("Username already taken!", HttpStatus.BAD_REQUEST);
        }
    }

    private void checkIfPhoneNumberExists(String phoneNumber) throws FieldExistedException {
        if (teacherRepository.existsByPhoneNumber(phoneNumber)) {
            throw new FieldExistedException("Phone number already taken!", HttpStatus.BAD_REQUEST);
        }
    }

    private void checkIfEmailExists(String email) throws FieldExistedException {
        if (teacherRepository.existsByEmail(email)) {
            throw new FieldExistedException("Email already taken!", HttpStatus.BAD_REQUEST);
        }
    }
}