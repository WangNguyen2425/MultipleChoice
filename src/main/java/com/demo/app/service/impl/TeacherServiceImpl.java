package com.demo.app.service.impl;

import com.demo.app.config.security.PasswordEncoder;
import com.demo.app.dto.teacher.TeacherRequest;
import com.demo.app.dto.teacher.TeacherResponse;
import com.demo.app.exception.UsernameExistException;
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
    public void saveTeacher(TeacherRequest request) throws UsernameExistException {
        if (userRepository.existsByUsername(request.getUsername())){
            throw new UsernameExistException("Username already taken !");
        }

        Role userRole = roleRepository.findByRoleName(Role.RoleType.ROLE_USER).get();
        Role teacherRole = roleRepository.findByRoleName(Role.RoleType.ROLE_TEACHER).get();
        List<Role> roles = new ArrayList<>();
        roles.add(userRole);
        roles.add(teacherRole);

        User user = new User();
        user.setUsername(request.getUsername());
        String encodePassword = passwordEncoder.passwordEncode().encode(request.getPassword());
        user.setPassword(encodePassword);
        user.setRoles(roles);

        Teacher teacher = modelMapper.map(request, Teacher.class);
        teacher.setUser(user);

        userRepository.save(user);
        teacherRepository.save(teacher);

    }

    @Override
    public List<TeacherResponse> getAllTeacher(){
        List<Teacher> teachers = teacherRepository.findAll();
        List<TeacherResponse> teacherResponses = new ArrayList<>();
        teachers.forEach(teacher -> {
            TeacherResponse teacherResponse = modelMapper.map(teacher, TeacherResponse.class);
            teacherResponse.setUsername(teacher.getUser().getUsername());
            teacherResponses.add(teacherResponse);
        });
        return teacherResponses;
    }
}
