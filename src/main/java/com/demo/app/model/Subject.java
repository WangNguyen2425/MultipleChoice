package com.demo.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "tbl_subject", schema = "dbo")
@Getter
@Setter
@NoArgsConstructor
public class Subject implements Serializable {
    @Id
    @Column(name = "subject_id")
    private String subjectId;

    @Column(name = "title")
    private String title;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
    private Set<Question> questions;

}
