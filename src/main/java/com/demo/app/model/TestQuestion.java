package com.demo.app.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "tbl_test_question", schema = "dbo")
public class TestQuestion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_question_id")
    private int testQuestionId;

    @ManyToOne
    @JoinColumn(name = "test_id", referencedColumnName = "test_id")
    private Test test;

    @ManyToOne
    @JoinColumn(name = "question_id", referencedColumnName = "question_id")
    private Question question;

}
