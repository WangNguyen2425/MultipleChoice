package com.demo.app.model;

import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "exam_class")
@Getter
@Setter
@NoArgsConstructor
public class ExamClass implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "semester")
    private String semester;

    @Column(name = "room_name")
    private String roomName;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "status")
    private boolean status;

    @ManyToOne
    private Teacher teacher;

    @ManyToOne
    private Subject subject;

    @OneToMany(mappedBy = "examClass", cascade = CascadeType.ALL)
    private List<StudentExamClass> studentExamClasses;

    @ManyToOne
    private Test test;

    @PrePersist
    private void prePersist(){
        createdDate = LocalDate.now();
    }

}
