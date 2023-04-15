package com.demo.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "answer")
@Getter
@Setter
@NoArgsConstructor
public class Answer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "content")
    private String content;

    @Column(name = "is_corrected", columnDefinition = "BIT(1) DEFAULT 0")
    private boolean isCorrected;

    @Column(name = "status")
    private boolean status;

    @ManyToOne
    private Question question;

}
