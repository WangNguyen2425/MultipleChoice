package com.demo.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "tbl_teacher", schema = "dbo")
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
   @Column(name = "gender")
   private Gender gender;

   @Column(name = "username")
   private String username;

   @Column(name = "password")
   private String password;

   @Column(name = "status")
   private boolean status;

   @ManyToOne
   private Role role;

   @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
   private List<TeacherSubject> teacherSubjects;

   @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
   private List<Classroom> classrooms;
}
