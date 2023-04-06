package com.demo.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name="tbl_test_set_detail_answer", schema = "dbo")
@Getter
@Setter
@NoArgsConstructor
public class TestSetDetailAnswer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_set_detail_answer_id")
    private int testSetDetailAnswerId;

    @Column(name = "question_no")
    private int questionNo;

    @ManyToOne
    @JoinColumn(name = "answer_id", referencedColumnName = "answer_id")
    private Answer answer;

    @ManyToOne
    @JoinColumn(name = "test_set_detail_id", referencedColumnName = "test_set_detail_id")
    private TestSetDetail testSetDetail;
}
