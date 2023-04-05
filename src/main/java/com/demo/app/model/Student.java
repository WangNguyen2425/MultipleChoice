package com.demo.app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "tbl_student", schema = "dbo")
@Getter
@Setter
@NoArgsConstructor
public class Student implements Serializable {
    @Id
    @Column(name = "student_id")
    private String studentId;


}
