package com.demo.app.model;

import javax.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "student_exam_class")
public class StudentExamClass implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Student student;

    @ManyToOne
    private ExamClass examClass;
}
