package com.demo.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "tbl_test_detail", schema = "dbo")
@Getter
@Setter
@NoArgsConstructor
public class TestDetail implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "status")
    private boolean status;

    @ManyToOne
    private Test test;

    @ManyToOne
    private Question question;

}
