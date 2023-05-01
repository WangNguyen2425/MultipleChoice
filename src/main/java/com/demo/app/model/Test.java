package com.demo.app.model;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
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

    @Column(name = "status")
    private boolean status = true;

    @Column(name = "test_day")
    private LocalDate testDay;

    @Column(name = "total_point")
    private double totalPoint;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL)
    private Set<TestSet> testSets;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "test_question",
            joinColumns = @JoinColumn(name = "test_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "question_id", referencedColumnName = "id")
    )
    private Set<Question> questions;

    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL)
    private List<ExamClass> examClasses;

    @PrePersist
    private void prePersist(){
        createdAt = LocalDate.now();
    }
    @PreUpdate
    private void preUpdate(){
        updatedAt = LocalDate.now();
    }
}
