package com.demo.app.service.impl;

import com.demo.app.config.security.PasswordEncoder;
import com.demo.app.dto.page.PageResponse;
import com.demo.app.dto.student.StudentRequest;
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
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper;

    @Override
    public void saveStudentsExcelFile(MultipartFile file) throws FileInputException, FieldExistedException {
        if (!ExcelUtils.hasExcelFormat(file)) {
            throw new FileInputException("Please upload an excel file!", HttpStatus.BAD_REQUEST);
        }
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
    public ByteArrayInputStream exportStudentsExcel() throws FileInputException {
        try {
            var students = studentRepository.findAll();
            return ExcelUtils.studentsToExcelFile(students);
        } catch (IOException ex){
            throw new FileInputException("Could not write the file !", HttpStatus.EXPECTATION_FAILED);
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
        user.setEnabled(true);
        userRepository.save(user);
    }

    @Override
    public PageResponse<StudentResponse> getAllStudents(int pageNo, int pageSize, String sortBy, String sortDir) {
        var sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        var pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Student> students = studentRepository.findAll(pageable);
        var studentResponses = students.stream().map(
                student -> modelMapper.map(student, StudentResponse.class)
        ).collect(Collectors.toList());

        return PageResponse.<StudentResponse>builder()
                .objects(studentResponses)
                .pageNo(students.getNumber())
                .pageSize(students.getSize())
                .totalElements(students.getTotalElements())
                .totalPages(students.getTotalPages())
                .isFirst(students.isFirst())
                .isLast(students.isLast())
                .build();
    }

    @Override
    public List<StudentResponse> getAllStudents() throws EntityNotFoundException {
        List<Student> students = studentRepository.findByEnabled(true);
        if (students.size() == 0) {
            throw new EntityNotFoundException("Not found any students", HttpStatus.NOT_FOUND);
        }
        return students.stream().map(student -> {
            StudentResponse response = modelMapper.map(student, StudentResponse.class);
            response.setUsername(student.getUser().getUsername());
            response.setEmail(student.getUser().getEmail());
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
        if (!existStudent.getUser().getEmail().equals(request.getEmail())) {
            checkIfEmailExists(request.getEmail());
        }

        var student = modelMapper.map(request, Student.class);
        student.setJoinDate(existStudent.getJoinDate());
        student.setUser(existStudent.getUser());
        student.setId(existStudent.getId());
        var user = student.getUser();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setUsername(request.getUsername());
        studentRepository.save(student);
    }

    @Override
    public void disableStudent(int studentId) throws EntityNotFoundException {
        var existStudent = studentRepository.findById(studentId).orElseThrow(
                () -> new EntityNotFoundException(String.format("Not found any student with id = %d !", studentId), HttpStatus.NOT_FOUND)
        );
        existStudent.getUser().setEnabled(false);
        studentRepository.save(existStudent);
    }

    @Override
    @Transactional
    public void deleteStudent(int studentId) throws EntityNotFoundException {
        var existStudent = studentRepository.findById(studentId).orElseThrow(
                () -> new EntityNotFoundException(String.format("Not found any student with id = %d !", studentId), HttpStatus.NOT_FOUND)
        );
        User user = existStudent.getUser();
        user.setRoles(null);

        userRepository.save(user);
        studentRepository.delete(existStudent);
        userRepository.delete(user);

    }

    private void checkIfUsernameExists(String username) throws FieldExistedException {
        if (userRepository.existsByUsername(username)) {
            throw new FieldExistedException("Username already taken!", HttpStatus.CONFLICT);
        }
    }

    private void checkIfPhoneNumberExists(String phoneNumber) throws FieldExistedException {
        if (studentRepository.existsByPhoneNumber(phoneNumber)) {
            throw new FieldExistedException("Phone number already taken!", HttpStatus.CONFLICT);
        }
    }

    private void checkIfEmailExists(String email) throws FieldExistedException {
        if (userRepository.existsByEmail(email)) {
            throw new FieldExistedException("Email already taken!", HttpStatus.CONFLICT);
        }
    }
}
