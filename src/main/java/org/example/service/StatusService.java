package org.example.service;

import org.example.entity.Status;
import org.example.payload.request.StatusRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.util.List;

public interface StatusService {
    public void saveStatus(Status status);

    public ResponseEntity<?> createStatus(StatusRequest statusRequest);

    public ResponseEntity<?> editStatus(StatusRequest statusRequest);

    public List<Status> findAll();

    public ResponseEntity<?> findByUserId(Integer userId);

    public ResponseEntity<?> deleteById(Integer id);

    public Status findByStatusId(Integer id);

    public ResponseEntity<?> findStatus(Integer id);
    public List<Status> findByUserUserIdAndDateOrderByStatusId(Integer userId, Date date);

    public Integer countByUserUserIdAndDateGreaterThanEqual(Integer userId, Date date);

    public List<Status> findByUserUserIdOrderByDate(Integer userId);

    public List<Status> findNewStatus(Pageable pageable);

    public Boolean existsByUserUserIdAndStatusId (Integer userId, Integer statusId);
}
