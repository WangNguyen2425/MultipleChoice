package com.demo.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

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

    @PrePersist
    private void prePersist(){
        createdDate = LocalDate.now();
    }

}
