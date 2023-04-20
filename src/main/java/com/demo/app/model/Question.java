package com.demo.app.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "question")
@Getter
@Setter
@NoArgsConstructor
public class Question implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private Chapter chapter;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Answer> answers;

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
