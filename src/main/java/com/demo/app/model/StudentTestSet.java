package com.demo.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "tbl_student_test_set")
@Getter
@Setter
@NoArgsConstructor
public class StudentTestSet implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "mark")
    private double mark;

    @ManyToOne
    private Student student;

    @Column(name = "status")
    private boolean status;

    @ManyToOne
    private TestSet testSet;

    @OneToMany(mappedBy = "studentTestSet", cascade = CascadeType.ALL)
    private List<StudentTestSetDetail> studentTestSetDetails;
}
