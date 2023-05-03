package com.demo.app.model;

import javax.persistence.*;

import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "chapter")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Chapter implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "chapter_name")
    private String chapterName;

    @Column(name = "chapter_no")
    private int chapterNo;

    @Column(name = "status")
    private boolean status = true;

    @ManyToOne
    private Subject subject;

    @OneToMany(mappedBy = "chapter", cascade = CascadeType.ALL)
    private Set<Question> questions;
}
