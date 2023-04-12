package com.demo.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "tbl_student", schema = "dbo")
@Getter
@Setter
@NoArgsConstructor
public class Student implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "join_date")
    private LocalDate joinDate;

    @Column(name = "phone_number")
    private String phoneNumber;

    private boolean status;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<StudentSubject> studentSubjects;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<StudentTestSet> studentTestSets;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<StudentClass> studentClasses;

    @PrePersist
    private void prePersist(){
        joinDate = LocalDate.now();
    }

}
