package com.demo.app.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "tbl_test_set", schema = "dbo")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class TestSet implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_set_id")
    private int testSetId;

    @Column(name = "test_no")
    private int testNo;

    @ManyToOne
    @JoinColumn(name = "test_id", referencedColumnName = "test_id")
    private Test test;


}
