package com.demo.app.model;

import jakarta.persistence.*;

import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "test_set_question_answer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestSetQuestionAnswer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "answer_no")
    private int answerNo;

    @Column(name = "status")
    private boolean status = true;

    @ManyToOne
    private TestSetQuestion testSetQuestion;
}
