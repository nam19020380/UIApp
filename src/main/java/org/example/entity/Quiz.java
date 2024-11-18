package org.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Table(name = "Quiz")
@Entity
@Getter
@Setter
public class Quiz {
    @Id
    @GeneratedValue
    @Column(name = "QUIZ_ID")
    private Integer quizId;

    @Column(name = "QUESTION")
    private String question;
}
