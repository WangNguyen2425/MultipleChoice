package com.demo.app.repository;

import com.demo.app.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    @Query(value = "SELECT s, u FROM Student s INNER JOIN User u ON s.user.id = u.id WHERE u.username = :username")
    public List<Object[]> getUserStudentByUsername(@Param("username") String username);
}
