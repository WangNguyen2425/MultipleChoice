package com.demo.app.model;

import javax.persistence.*;

import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "answer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Answer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "content")
    private String content;

    @Column(name = "is_corrected")
    private boolean isCorrected;

    @Column(name = "status")
    private boolean status = true;

    @ManyToOne
    private Question question;

}
