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
    private int id;

    @Column(name = "question_no")
    private int questionNo;

    @Column(name = "status")
    private boolean status;

    @ManyToOne
    private Answer answer;

    @ManyToOne
    private TestSetDetail testSetDetail;
}
