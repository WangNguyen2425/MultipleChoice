package com.demo.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "student_test_detail_answer")
@Getter
@Setter
@NoArgsConstructor
public class StudentTestDetailAnswer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "status")
    private boolean status;

    @Column(name = "is_selected")
    private boolean isSelected;

    @Column(name = "is_corrected")
    private boolean isCorrected;

    @ManyToOne
    private StudentTestDetail studentTestDetail;
}
