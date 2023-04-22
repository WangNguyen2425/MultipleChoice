package com.demo.app.model;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "test_set_question")
@Getter
@Setter
@NoArgsConstructor
public class TestSetQuestion implements Serializable {
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

    @OneToOne(mappedBy = "testSetQuestion", cascade = CascadeType.ALL)
    private StudentTestDetail studentTestDetail;

    @OneToMany(mappedBy = "testSetQuestion", cascade = CascadeType.ALL)
    private List<TestSetQuestionAnswer> testSetQuestionAnswers;

}
