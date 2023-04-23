package com.demo.app.service.impl;

import com.demo.app.model.Role;
import com.demo.app.model.Student;
import com.demo.app.model.User;
import com.demo.app.repository.RoleRepository;
import com.demo.app.repository.StudentRepository;
import com.demo.app.repository.UserRepository;
import com.demo.app.service.StudentService;
import com.demo.app.util.ExcelUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Override
    public void saveStudentsExcelFile(MultipartFile file) throws IOException {
        Map<User, Student> userStudents = ExcelUtils.excelFileToUserStudents(file);
        Role roleUser = roleRepository.findByRoleName(Role.RoleType.ROLE_USER.toString()).get();
        Role roleStudent = roleRepository.findByRoleName(Role.RoleType.ROLE_STUDENT.toString()).get();
        List<Role> roles = new ArrayList<>();
        roles.add(roleUser);
        roles.add(roleStudent);

        userStudents.forEach((user, student) -> {
            user.setRoles(roles);

            student.setUser(user);
        });

        studentRepository.saveAll(userStudents.values());
        userRepository.saveAll(userStudents.keySet());

    }

}
