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
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student student;

    @Column(name = "status")
    private boolean status;

    @ManyToOne
    @JoinColumn(name = "test_set_id", referencedColumnName = "test_set_id")
    private TestSet testSet;

    @OneToMany(mappedBy = "studentTestSet", cascade = CascadeType.ALL)
    private List<StudentTestSetDetail> studentTestSetDetails;
}
