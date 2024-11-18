package org.example.repository;

import org.example.entity.Record;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecordRepository extends JpaRepository<Record, String> {
    public List<Record> findByQuizQuizId(Integer quizId, Pageable pageable);
    public Page<Record> findAll(Pageable pageable);
    public Integer countByQuizQuizId(Integer quizId);
    public Record findByNameAndQuizQuizId(String name, Integer quizId);
}
