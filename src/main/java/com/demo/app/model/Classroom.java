package com.demo.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "tbl_classroom", schema = "dbo")
@Getter
@Setter
@NoArgsConstructor
public class Classroom implements Serializable {
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

    @OneToMany(mappedBy = "classroom", cascade = CascadeType.ALL)
    private List<StudentClass> studentClasses;

    @ManyToOne
    private Teacher teacher;

    @PrePersist
    private void prePersist(){
        createdDate = LocalDate.now();
    }

}
