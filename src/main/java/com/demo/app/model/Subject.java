package com.demo.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "subject", schema = "dbo")
@Getter
@Setter
@NoArgsConstructor
public class Subject implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "status")
    private boolean status;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
    private List<TeacherSubject> teacherSubjects;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
    private List<StudentSubject> studentSubjects;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
    private List<Chapter> chapters;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
    private List<ExamClass> examClasses;
}
