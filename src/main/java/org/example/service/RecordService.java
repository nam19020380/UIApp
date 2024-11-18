package org.example.service;

import org.example.entity.Record;
import org.example.payload.request.QuizRequest;
import org.example.payload.response.QuizResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RecordService {
    public Record findById(Integer id);

    public void saveRecord (Record record);

    public void deleteRecord (Integer id);

    public List<Record> getRecordByQuizId(Integer id, Pageable pageable);

    public Integer getRecordCountByQuizId(Integer id);

    public Record getByNameAndQuizId(String name, Integer id);
}
