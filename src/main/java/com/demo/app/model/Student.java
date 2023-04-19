package com.demo.app.model;

import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "student", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"email"}, name = "uni_email"),
        @UniqueConstraint(columnNames = {"phone_number"}, name = "uni_phone_number")
})
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

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;


    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<StudentTest> studentTests;

    @OneToOne
    @JoinColumn(referencedColumnName = "id", name = "user_id")
    private User user;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<StudentExamClass> studentExamClasses;

    @PrePersist
    private void prePersist(){
        joinDate = LocalDate.now();
    }

}
