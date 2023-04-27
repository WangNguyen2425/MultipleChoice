package com.demo.app.service.impl;

import com.demo.app.config.security.PasswordEncoder;
import com.demo.app.dto.student.StudentRequest;
import com.demo.app.dto.student.StudentPageResponse;
import com.demo.app.dto.student.StudentResponse;
import com.demo.app.exception.StudentNotFoundException;
import com.demo.app.exception.UsernameExistException;
import com.demo.app.model.Role;
import com.demo.app.model.Student;
import com.demo.app.model.User;
import com.demo.app.repository.RoleRepository;
import com.demo.app.repository.StudentRepository;
import com.demo.app.repository.UserRepository;
import com.demo.app.service.StudentService;
import com.demo.app.util.ExcelUtils;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
    public void saveStudentsExcelFile(MultipartFile file) throws IOException {
        Map<User, Student> userStudents = ExcelUtils.excelFileToUserStudents(file);
        List<Role> roles = getRoleUserAndStudent();

        userStudents.forEach((user, student) -> {
            user.setRoles(roles);
            String encodePassword = passwordEncoder.passwordEncode().encode(user.getPassword());
            user.setPassword(encodePassword);
            student.setUser(user);
        });
        userRepository.saveAll(userStudents.keySet());
        studentRepository.saveAll(userStudents.values());
    }

    @Override
    @Transactional
    public void saveStudent(StudentRequest request) throws UsernameExistException {
        if (userRepository.existsByUsername(request.getUsername())){
            throw new UsernameExistException("Username already taken !");
        }
        List<Role> roles = getRoleUserAndStudent();

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.passwordEncode().encode(request.getPassword()));
        user.setRoles(roles);
        user = userRepository.save(user);

        Student student = modelMapper.map(request, Student.class);
        student.setUser(user);
        studentRepository.save(student);
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
    public List<StudentResponse> getAllStudents(){
        List<Student> students = studentRepository.findAll();
        List<StudentResponse> studentResponses = new ArrayList<>();
        students.forEach(student -> {
            StudentResponse studentResponse = modelMapper.map(student, StudentResponse.class);
            studentResponse.setUsername(student.getUser().getUsername());
            studentResponses.add(studentResponse);
        });
        return studentResponses;
    }

    @Override
    public void updateStudent(int studentId, StudentRequest request) throws StudentNotFoundException {
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        if (optionalStudent.isEmpty()){
            throw new StudentNotFoundException(String.format("Student %s not found !", request.getUsername()));
        }
        Student student = modelMapper.map(request, Student.class);
        student.setId(optionalStudent.get().getId());
        studentRepository.save(student);
    }

    private List<Role> getRoleUserAndStudent(){
        Role roleUser = roleRepository.findByRoleName(Role.RoleType.ROLE_USER).get();
        Role roleStudent = roleRepository.findByRoleName(Role.RoleType.ROLE_STUDENT).get();
        List<Role> roles = new ArrayList<>();
        roles.add(roleUser);
        roles.add(roleStudent);

        return roles;
    }
}
