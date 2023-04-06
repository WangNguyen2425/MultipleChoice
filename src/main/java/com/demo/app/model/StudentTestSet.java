package com.demo.app.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "tbl_student_test_set")
public class StudentTestSet implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_test_set_id")
    private int studentTestSetId;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "test_set_id", referencedColumnName = "test_set_id")
    private TestSet testSet;

    @OneToMany(mappedBy = "studentTestSet", cascade = CascadeType.ALL)
    private List<StudentTestSetDetail> studentTestSetDetails;
}
