package org.example.service;

import org.example.entity.Answer;
import org.example.entity.Quiz;
import org.example.payload.request.QuizRequest;
import org.example.payload.response.QuizResponse;
import org.example.repository.AnswerRepository;
import org.example.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuizServiceImpl implements QuizService{
    @Autowired
    QuizRepository quizRepository;

    @Autowired
    AnswerRepository answerRepository;

    @Override
    public Quiz findById(Integer id) {
        return quizRepository.findById(String.valueOf(id)).get();
    }

    @Override
    public void saveQuiz(QuizRequest quizRequest) {
        Quiz quiz = new Quiz();
        quiz.setQuizId(quizRequest.getId());
        quiz.setQuestion(quizRequest.getQuestion());
        quiz = quizRepository.save(quiz);
        answerRepository.deleteByQuizQuizId(quiz.getQuizId());
        for(Answer answer : quizRequest.getAnswers()){
            answer.setQuiz(quiz);
            answerRepository.save(answer);
        }
    }

    @Override
    public void deleteQuiz(Integer id) {
        quizRepository.deleteById(String.valueOf(id));
    }

    @Override
    public QuizResponse getQuiz(Integer id){
        QuizResponse quizResponse = new QuizResponse();
        quizResponse.setQuiz(quizRepository.findById(String.valueOf(id)).get());
        quizResponse.setAnswerList(answerRepository.findByQuizQuizId(id));
        return quizResponse;
    }
}
