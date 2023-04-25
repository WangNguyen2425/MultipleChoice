package com.demo.app.service.impl;

import com.demo.app.config.security.PasswordEncoder;
import com.demo.app.dto.TeacherDto;
import com.demo.app.model.Role;
import com.demo.app.model.Teacher;
import com.demo.app.model.User;
import com.demo.app.repository.RoleRepository;
import com.demo.app.repository.TeacherRepository;
import com.demo.app.repository.UserRepository;
import com.demo.app.service.TeacherService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

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
    public void saveTeacher(TeacherDto teacherDto) {

        Role userRole = roleRepository.findByRoleName(Role.RoleType.ROLE_USER).get();
        Role teacherRole = roleRepository.findByRoleName(Role.RoleType.ROLE_TEACHER).get();
        List<Role> roles = new ArrayList<>();
        roles.add(userRole);
        roles.add(teacherRole);

        User user = new User();
        user.setUsername(teacherDto.getUsername());
        String encodePassword = passwordEncoder.passwordEncode().encode(teacherDto.getPassword());
        user.setPassword(encodePassword);
        user.setRoles(roles);

        Teacher teacher = modelMapper.map(teacherDto, Teacher.class);
        teacher.setUser(user);

        userRepository.save(user);
        teacherRepository.save(teacher);

    }
}
