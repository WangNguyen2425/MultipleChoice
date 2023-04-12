package com.demo.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "tbl_question", schema = "dbo")
@Getter
@Setter
@NoArgsConstructor
public class Question implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "topic_text")
    private String topicText;

    @Column(name = "topic_image")
    private byte[] topicImage;

    @Enumerated(EnumType.STRING)
    @Column(name = "level")
    private Level level;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "status")
    private boolean status;

    @ManyToOne
    @JoinColumn(name = "subject_id", referencedColumnName = "id")
    private Subject subject;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Answer> answers;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<TestSetDetail> testSetDetails;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<TestDetail> testDetails;

    public enum Level{
        EASY,
        NORMAL,
        DIFFICULT
    }

    @PrePersist
    private void prePersist(){
        createdDate = LocalDate.now();
    }
}
