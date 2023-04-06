package com.demo.app.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "tbl_student_test_set_detail")
public class StudentTestSetDetail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_test_set_detail_id")
    private int studentTestSetDetailId;

    @Column(name = "mark")
    private double mark;

    @ManyToOne
    @JoinColumn(name = "student_test_set_id", referencedColumnName = "student_test_set_id")
    private StudentTestSet studentTestSet;
}
