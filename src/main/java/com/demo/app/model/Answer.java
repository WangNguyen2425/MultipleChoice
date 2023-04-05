package com.demo.app.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "tbl_answer", schema = "dbo")
public class Answer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private int answerId;

    @Column(name = "content")
    private String content;

    @Column(name = "is_corrected", columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean isCorrected;
}
