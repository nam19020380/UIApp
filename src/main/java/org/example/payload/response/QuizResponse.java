package org.example.payload.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.entity.Answer;
import org.example.entity.Quiz;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class QuizResponse {
    private Quiz quiz;
    private List<Answer> answerList;
}
