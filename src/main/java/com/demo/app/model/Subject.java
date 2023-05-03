package com.demo.app.model;

import javax.persistence.*;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "subject")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subject implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "status")
    private boolean status = true;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
    private List<Chapter> chapters;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
    private List<ExamClass> examClasses;
}
