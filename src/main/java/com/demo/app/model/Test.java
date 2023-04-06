package com.demo.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tbl_test", schema = "dbo")
@Getter
@Setter
@NoArgsConstructor
public class Test implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "test_id")
    private String testId;

    @Column(name = "test_date")
    private LocalDate testDate;

    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL)
    private Set<TestSet> testSets;

    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL)
    private List<TestQuestion> testQuestions;
}
