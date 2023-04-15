package com.demo.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "student_test_detail")
@Getter
@Setter
@NoArgsConstructor
public class StudentTestDetail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "status")
    private boolean status;

    @ManyToOne
    private StudentTest studentTest;

    @OneToOne
    private TestSetQuestion testSetQuestion;

    @OneToMany(mappedBy = "studentTestDetail", cascade = CascadeType.ALL)
    private List<StudentTestDetailAnswer> studentTestDetailAnswers;
}
