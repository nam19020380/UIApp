package org.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name = "ANSWER")
@Entity
@Getter
@Setter
public class Answer {
    @Id
    @GeneratedValue
    @Column(name = "ANSWER_ID")
    private Integer answerId;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "RESULT")
    private boolean result;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "QUIZ_ID", nullable = false)
    Quiz quiz;
}
