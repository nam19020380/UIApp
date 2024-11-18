package org.example.service;

import org.example.repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.example.entity.Record;

import java.util.List;

@Service
public class RecordServiceImpl implements RecordService{
    @Autowired
    RecordRepository recordRepository;

    @Override
    public Record findById(Integer id) {
        return recordRepository.findById(String.valueOf(id)).get();
    }

    @Override
    public void saveRecord(Record record) {
        recordRepository.save(record);
    }

    @Override
    public void deleteRecord(Integer id) {
        recordRepository.deleteById(String.valueOf(id));
    }

    @Override
    public List<Record> getRecordByQuizId(Integer id, Pageable pageable) {
        return recordRepository.findByQuizQuizId(id, pageable);
    }

    @Override
    public Integer getRecordCountByQuizId(Integer id) {
        return recordRepository.countByQuizQuizId(id);
    }

    @Override
    public Record getByNameAndQuizId(String name, Integer id) {
        return recordRepository.findByNameAndQuizQuizId(name, id);
    }
}
