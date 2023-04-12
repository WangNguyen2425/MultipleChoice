package com.demo.app.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "tbl_test_set", schema = "dbo")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class TestSet implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "test_no")
    private int testNo;

    @Column(name = "status")
    private boolean status;

    @ManyToOne
    private Test test;

    @OneToMany(mappedBy = "testSet", cascade = CascadeType.ALL)
    private List<TestSetDetail> testSetDetails;

    @OneToMany(mappedBy = "testSet", cascade = CascadeType.ALL)
    private List<StudentTestSet> studentTestSets;
}
