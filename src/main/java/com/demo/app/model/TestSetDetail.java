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
    @Column(name = "id")
    private int id;

    @Column(name = "question_no")
    private int questionNo;

    @Column(name = "status")
    private boolean status;

    @ManyToOne
    @JoinColumn(name = "test_set_id", referencedColumnName = "test_set_id")
    private TestSet testSet;

    @ManyToOne
    @JoinColumn(name = "question_id", referencedColumnName = "question_id")
    private Question question;

    @OneToMany(mappedBy = "testSetDetail", cascade = CascadeType.ALL)
    private List<TestSetDetailAnswer> testSetDetailAnswers;
}
