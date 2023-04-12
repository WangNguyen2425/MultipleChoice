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
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "status")
    private boolean status;

    public Admin(String username, String password, boolean status, Role role) {
        this.username = username;
        this.password = password;
        this.status = status;
        this.role = role;
    }

    @ManyToOne
    private Role role;
}
