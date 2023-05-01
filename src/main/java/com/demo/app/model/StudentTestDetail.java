package com.demo.app.model;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

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
    private boolean status = true;

    @Column(name = "question_mark", precision = 2, scale = 1)
    private double questionMark;

    @Column(name = "selected_answer", columnDefinition = "varchar(4)")
    private String selectedAnswer;

    @ManyToOne
    private StudentTest studentTest;

    @OneToOne
    private TestSetQuestion testSetQuestion;


}
