package com.demo.app.model;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "teacher", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email", name = "uni_email"),
        @UniqueConstraint(columnNames = "phone_number", name = "uni_phone_number")
})
@Getter
@Setter
@NoArgsConstructor
public class Teacher implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", length = 10)
    private Gender gender;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private List<ExamClass> examClasses;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "teacher_subject",
            joinColumns = @JoinColumn(name = "teacher_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id", referencedColumnName = "id"))
    private List<Subject> subjects;

    @OneToOne
    @JoinColumn(referencedColumnName = "id", name = "user_id")
    private User user;


}
