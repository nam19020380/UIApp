package org.example.controller;

import org.example.payload.request.QuizRequest;
import org.example.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/quiz")
public class QuizController {
    @Autowired
    QuizService quizService;

    @PostMapping
    public ResponseEntity<?> createQuiz(@RequestBody QuizRequest quizRequest) {
        try {
            quizService.saveQuiz(quizRequest);

            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Server Error");
        }
    }

    @PutMapping
    public ResponseEntity<?> editQuiz(@RequestBody QuizRequest quizRequest) {
        try {
            quizService.saveQuiz(quizRequest);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Server Error");
        }
    }

    @GetMapping
    public ResponseEntity<?> getQuiz(@RequestParam Integer quizId) {
        try {
            return new ResponseEntity<>(quizService.getQuiz(quizId), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Server Error");
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteQuiz(@RequestParam Integer quizId) {
        try {
            quizService.deleteQuiz(quizId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Server Error");
        }
    }
}
