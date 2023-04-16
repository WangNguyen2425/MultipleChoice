package com.demo.app;

import com.demo.app.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ProjectDesignIApplicationTests {

    @Autowired
    StudentRepository studentRepository;

    @Test
    void contextLoads() {
        List<Object[]> userStudent = studentRepository.getUserStudentByUsername("");
        for (Object[] us : userStudent){
            System.out.println(us[0]);
            System.out.println(us[1]);
        }
    }

}
