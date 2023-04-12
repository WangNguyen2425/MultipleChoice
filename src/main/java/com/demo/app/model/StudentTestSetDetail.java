package com.demo.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "tb l_student_test_set_detail")
@Getter
@Setter
@NoArgsConstructor
public class StudentTestSetDetail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "question_mark")
    private double questionMark;


    @Column(name = "status")
    private boolean status;

    @ManyToOne
    private StudentTestSet studentTestSet;

    @OneToMany(mappedBy = "studentTestSetDetail", cascade = CascadeType.ALL)
    private List<StudentTestSetDetailAnswer> studentTestSetDetailAnswers;
}
