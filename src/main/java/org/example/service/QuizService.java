package org.example.service;

import org.example.entity.Quiz;
import org.example.payload.request.QuizRequest;
import org.example.payload.response.QuizResponse;

public interface QuizService {
    public Quiz findById(Integer id);

    public void saveQuiz (QuizRequest quizRequest);

    public void deleteQuiz (Integer id);

    public QuizResponse getQuiz(Integer id);
}
