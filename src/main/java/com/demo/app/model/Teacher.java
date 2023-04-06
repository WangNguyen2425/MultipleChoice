package com.demo.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

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
   @GeneratedValue(generator = "UUID")
   @GenericGenerator(
           name = "UUID",
           strategy = "org.hibernate.id.UUIDGenerator"
   )
   @Column(name = "teacher_id")
   private String teacherId;

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

   @ManyToOne
   @JoinColumn(name = "role_id", referencedColumnName = "role_id")
   private Role role;

   @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
   private List<TeacherSubject> teacherSubjects;
}
