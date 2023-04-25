package com.demo.app.service.impl;

import com.demo.app.config.security.PasswordEncoder;
import com.demo.app.dto.StudentDto;
import com.demo.app.dto.StudentPageResponse;
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
    public void saveStudent(StudentDto studentDto){
        List<Role> roles = getRoleUserAndStudent();

        User user = new User();
        user.setUsername(studentDto.getUsername());
        user.setPassword(passwordEncoder.passwordEncode().encode(studentDto.getPassword()));
        user.setRoles(roles);
        user = userRepository.save(user);

        Student student = modelMapper.map(studentDto, Student.class);
        student.setUser(user);
        studentRepository.save(student);
    }

    @Override
    public StudentPageResponse getAllStudents(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Student> students = studentRepository.findAll(pageable);
        List<StudentDto> studentDtos = students.stream().map(student -> new ModelMapper().map(student, StudentDto.class)).collect(Collectors.toList());

        StudentPageResponse response = new StudentPageResponse();
        response.setStudentDtos(studentDtos);
        response.setPageNo(students.getNumber());
        response.setPageSize(students.getSize());
        response.setTotalElements(students.getTotalElements());
        response.setTotalPages(students.getTotalPages());
        response.setFirst(students.isFirst());
        response.setLast(students.isLast());
        return response;
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
