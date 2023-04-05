package com.demo.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "tbl_admin", schema = "dbo")
@Getter
@Setter
@NoArgsConstructor
public class Admin implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private int adminId;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    private Role role;
}
