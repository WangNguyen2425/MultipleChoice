package com.demo.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "student")
@Getter
@Setter
@NoArgsConstructor
public class Student implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "join_date")
    private LocalDate joinDate;

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @Column(name = "email", unique = true)
    private String email;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<StudentSubject> studentSubjects;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<StudentTest> studentTests;

    @OneToOne
    private User user;

    @PrePersist
    private void prePersist(){
        joinDate = LocalDate.now();
    }

}
