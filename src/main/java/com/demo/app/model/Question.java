package com.demo.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

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
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "question_id")
    private String questionId;

    @Column(name = "topic_text")
    private String topicText;

    @Column(name = "topic_image")
    private byte[] topicImage;

    @Enumerated(EnumType.STRING)
    @Column(name = "level")
    private Level level;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @ManyToOne
    @JoinColumn(name = "subject_id", referencedColumnName = "subject_id")
    private Subject subject;

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
