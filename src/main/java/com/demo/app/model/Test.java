package com.demo.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "test")
@Getter
@Setter
@NoArgsConstructor
public class Test implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "test_date")
    private LocalDate testDate;

    @Column(name = "status")
    private boolean status;

    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL)
    private Set<TestSet> testSets;

    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL)
    private Set<TestQuestion> testQuestions;
}
