package com.demo.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "student_test_set")
@Getter
@Setter
@NoArgsConstructor
public class StudentTest implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "mark")
    private double mark;

    @Column(name = "image")
    private byte[] image;

    @Column(name = "status")
    private boolean status;

    @ManyToOne
    private Student student;

    @ManyToOne
    private TestSet testSet;

    @OneToMany(mappedBy = "studentTest", cascade = CascadeType.ALL)
    private List<StudentTestDetail> studentTestDetails;

}
