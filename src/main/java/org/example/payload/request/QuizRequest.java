package org.example.payload.request;

import lombok.Getter;
import lombok.Setter;
import org.example.entity.Answer;

import java.util.List;

@Getter
@Setter
public class QuizRequest {
    private Integer id;
    private String question;
    private List<Answer> answers;
}
