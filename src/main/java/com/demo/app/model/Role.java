package com.demo.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "tbl_role", schema = "dbo")
@Getter
@Setter
@NoArgsConstructor
public class Role implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "status")
    private boolean status;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private List<Admin> admins;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private List<Student> students;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private List<Teacher> teachers;
}
