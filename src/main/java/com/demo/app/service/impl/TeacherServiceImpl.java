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
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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

    @Override
    @Transactional
    public void updateTeacher(int teacherId, TeacherRequest request) throws EntityNotFoundException, FieldExistedException{
        Teacher existTeacher = teacherRepository.findById(teacherId).
                orElseThrow(() -> new EntityNotFoundException(String.format("Teacher %s not found !", request.getFullName()), HttpStatus.NOT_FOUND));

        if (!existTeacher.getUser().getUsername().equals(request.getUsername())) {
            checkIfUsernameExists(request.getUsername());
        }
        if (!existTeacher.getPhoneNumber().equals(request.getPhoneNumber())) {
            checkIfPhoneNumberExists(request.getPhoneNumber());
        }
        if (!existTeacher.getEmail().equals(request.getEmail())) {
            checkIfEmailExists(request.getEmail());
        }

        Teacher teacher = modelMapper.map(request, Teacher.class);
        teacher.setId(existTeacher.getId());
        teacher.setUser(existTeacher.getUser());
        teacherRepository.save(teacher);
    }

    @Override
    public void disableTeacher(int teacherId) throws EntityNotFoundException{
        Teacher existTeacher = teacherRepository.findById(teacherId).
                orElseThrow(() -> new EntityNotFoundException(String.format("Not found any teacher with id = %d", teacherId), HttpStatus.NOT_FOUND));
        existTeacher.getUser().setStatus(false);
        teacherRepository.save(existTeacher);
    }

    @Override
    @Transactional
    public void deleteTeacher(int teacherId) throws EntityNotFoundException{
        Teacher existTeacher = teacherRepository.findById(teacherId).
                orElseThrow(() -> new EntityNotFoundException(String.format("Not found any teacher with id = %d", teacherId), HttpStatus.NOT_FOUND));
        User user = existTeacher.getUser();

        userRepository.deleteRoleFromUser(user.getId());
        teacherRepository.delete(existTeacher);
        userRepository.delete(user);
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
