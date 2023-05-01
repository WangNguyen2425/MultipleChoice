package com.demo.app.service.impl;

import com.demo.app.config.security.PasswordEncoder;
import com.demo.app.dto.student.StudentRequest;
import com.demo.app.dto.student.StudentPageResponse;
import com.demo.app.dto.student.StudentResponse;
import com.demo.app.exception.EntityNotFoundException;
import com.demo.app.exception.FieldExistedException;
import com.demo.app.exception.FileInputException;
import com.demo.app.model.Role;
import com.demo.app.model.Student;
import com.demo.app.model.User;
import com.demo.app.repository.RoleRepository;
import com.demo.app.repository.StudentRepository;
import com.demo.app.repository.UserRepository;
import com.demo.app.service.StudentService;
import com.demo.app.util.ExcelUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper;

    @Override
    public void saveStudentsExcelFile(MultipartFile file) throws FileInputException, FieldExistedException {
        try {
            Map<User, Student> userStudents = ExcelUtils.excelFileToUserStudents(file);

            List<Role> roles = roleRepository.findAllByRoleNameIn(Arrays.asList(Role.RoleType.ROLE_USER, Role.RoleType.ROLE_STUDENT));

            userStudents.forEach((user, student) -> {
                user.setRoles(roles);
                String encodePassword = passwordEncoder.passwordEncode().encode(user.getPassword());
                user.setPassword(encodePassword);
                student.setUser(user);
            });
            userRepository.saveAll(userStudents.keySet());
            studentRepository.saveAll(userStudents.values());
        } catch (IOException ex){
            throw new FileInputException("Could not read the file !", HttpStatus.EXPECTATION_FAILED);
        }
    }

    @Override
    @Transactional
    public void saveStudent(StudentRequest request) throws FieldExistedException {
        checkIfUsernameExists(request.getUsername());
        checkIfEmailExists(request.getEmail());
        checkIfPhoneNumberExists(request.getPhoneNumber());

        List<Role> roles = roleRepository.findAllByRoleNameIn(Arrays.asList(Role.RoleType.ROLE_USER, Role.RoleType.ROLE_STUDENT));

        User user = modelMapper.map(request, User.class);
        user.setPassword(passwordEncoder.passwordEncode().encode(request.getPassword()));
        user.setRoles(roles);
        user.getStudent().setUser(user);
        userRepository.save(user);
    }

    @Override
    public StudentPageResponse getAllStudents(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Student> students = studentRepository.findAll(pageable);
        List<StudentResponse> studentResponses = students.stream().map(student -> modelMapper.map(student, StudentResponse.class)).collect(Collectors.toList());

        StudentPageResponse response = new StudentPageResponse();
        response.setStudentDtos(studentResponses);
        response.setPageNo(students.getNumber());
        response.setPageSize(students.getSize());
        response.setTotalElements(students.getTotalElements());
        response.setTotalPages(students.getTotalPages());
        response.setFirst(students.isFirst());
        response.setLast(students.isLast());
        return response;
    }

    @Override
    public List<StudentResponse> getAllStudents() throws EntityNotFoundException {
        List<Student> students = studentRepository.findByStatus(true);
        if (students.size() == 0) {
            throw new EntityNotFoundException("Not found any students", HttpStatus.NOT_FOUND);
        }
        return students.stream().map(student -> {
            StudentResponse response = modelMapper.map(student, StudentResponse.class);
            response.setUsername(student.getUser().getUsername());
            return response;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateStudent(int studentId, StudentRequest request) throws EntityNotFoundException, FieldExistedException {
        Student existStudent = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Student %s not found !", request.getUsername()), HttpStatus.NOT_FOUND));
        if (!existStudent.getUser().getUsername().equals(request.getUsername())) {
            checkIfUsernameExists(request.getUsername());
        }
        if (!existStudent.getPhoneNumber().equals(request.getPhoneNumber())) {
            checkIfPhoneNumberExists(request.getPhoneNumber());
        }
        if (!existStudent.getEmail().equals(request.getEmail())) {
            checkIfEmailExists(request.getEmail());
        }

        Student student = modelMapper.map(request, Student.class);
        student.setJoinDate(existStudent.getJoinDate());
        student.setUser(existStudent.getUser());
        student.setId(existStudent.getId());

        studentRepository.save(student);
    }

    @Override
    public void disableStudent(int studentId) throws EntityNotFoundException {
        Student existStudent = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Not found any student with id = %d !", studentId), HttpStatus.NOT_FOUND));
        existStudent.getUser().setStatus(false);
        studentRepository.save(existStudent);
    }

    @Override
    @Transactional
    public void deleteStudent(int studentId) throws EntityNotFoundException {
        Student existStudent = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Not found any student with id = %d !", studentId), HttpStatus.NOT_FOUND));
        User user = existStudent.getUser();

        userRepository.deleteRoleFromUser(user.getId());
        studentRepository.delete(existStudent);
        userRepository.delete(user);

    }

    private void checkIfUsernameExists(String username) throws FieldExistedException {
        if (userRepository.existsByUsername(username)) {
            throw new FieldExistedException("Username already taken!", HttpStatus.BAD_REQUEST);
        }
    }

    private void checkIfPhoneNumberExists(String phoneNumber) throws FieldExistedException {
        if (studentRepository.existsByPhoneNumber(phoneNumber)) {
            throw new FieldExistedException("Phone number already taken!", HttpStatus.BAD_REQUEST);
        }
    }

    private void checkIfEmailExists(String email) throws FieldExistedException {
        if (studentRepository.existsByEmail(email)) {
            throw new FieldExistedException("Email already taken!", HttpStatus.BAD_REQUEST);
        }
    }
}
