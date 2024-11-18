package org.example.repository;

import org.example.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, String> {
    public void deleteByQuizQuizId(Integer quizId);
    public List<Answer> findByQuizQuizId(Integer quizId);
}
