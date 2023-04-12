package com.demo.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "tbl_test_set_detail", schema = "dbo")
@Getter
@Setter
@NoArgsConstructor
public class TestSetDetail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "question_no")
    private int questionNo;

    @Column(name = "status")
    private boolean status;

    @ManyToOne
    private TestSet testSet;

    @ManyToOne
    private Question question;

    @OneToMany(mappedBy = "testSetDetail", cascade = CascadeType.ALL)
    private List<TestSetDetailAnswer> testSetDetailAnswers;
}
