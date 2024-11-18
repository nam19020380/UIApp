package org.example.controller;

import org.example.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.entity.Record;

@RestController
@RequestMapping(value = "/api/v1/record")
public class RecordController {
    @Autowired
    RecordService recordService;

    @PostMapping
    public ResponseEntity<?> createRecord(@RequestBody Record record) {
        try {
            recordService.saveRecord(record);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Server Error");
        }
    }

    @PutMapping
    public ResponseEntity<?> editRecord(@RequestBody Record record) {
        try {
            recordService.saveRecord(record);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Server Error");
        }
    }

    @GetMapping
    public ResponseEntity<?> getRecord(@RequestParam Integer id) {
        try {
            return new ResponseEntity<>(recordService.findById(id), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Server Error");
        }
    }

    @GetMapping(value = "/quiz")
    public ResponseEntity<?> getRecord(@RequestParam Integer id
            , @RequestParam Integer page, @RequestParam Integer perPage) {
        try {
            Pageable pageable = PageRequest.of(page, perPage, Sort.by("score").descending());
            return new ResponseEntity<>(recordService.getRecordByQuizId(id, pageable), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Server Error");
        }
    }

    @GetMapping(value = "/quizJoinCount")
    public ResponseEntity<?> getRecordCount(@RequestParam Integer id) {
        try {
            return new ResponseEntity<>(recordService.getRecordCountByQuizId(id), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Server Error");
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteRecord(@RequestParam Integer id) {
        try {
            recordService.deleteRecord(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Server Error");
        }
    }
}
