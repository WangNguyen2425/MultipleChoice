package com.demo.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "tbl_student_test_set_detail")
@Getter
@Setter
@NoArgsConstructor
public class StudentTestSetDetail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "mark")
    private double mark;

    @Column(name = "status")
    private boolean status;

    @ManyToOne
    private StudentTestSet studentTestSet;
}
