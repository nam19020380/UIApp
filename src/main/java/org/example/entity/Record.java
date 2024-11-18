package org.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name = "RECORD")
@Entity
@Getter
@Setter
public class Record {
    @Id
    @GeneratedValue
    @Column(name = "RECORD_ID")
    private Integer quizId;

    @Column(name = "SCORE")
    private String score;

    @Column(name = "NAME")
    private String name;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "QUIZ_ID", nullable = false)
    Quiz quiz;
}
