package com.demo.app.model;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "test_set_question_answer")
@Getter
@Setter
@NoArgsConstructor
public class TestSetQuestionAnswer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "answer_no")
    private int answerNo;

    @Column(name = "status")
    private boolean status;

    @ManyToOne
    private TestSetQuestion testSetQuestion;
}
