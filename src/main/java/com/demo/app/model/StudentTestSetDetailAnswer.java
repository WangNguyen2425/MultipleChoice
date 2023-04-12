package com.demo.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "student_test_set_detail_answer", schema = "dbo")
@Getter
@Setter
@NoArgsConstructor
public class StudentTestSetDetailAnswer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int questionNo;

    private boolean isSelected;

    @ManyToOne
    private StudentTestSetDetail studentTestSetDetail;

}
