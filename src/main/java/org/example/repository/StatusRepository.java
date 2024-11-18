package org.example.repository;

import jakarta.transaction.Transactional;
import org.example.entity.Status;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
@Transactional
@EntityScan("org.example.entity")
public interface StatusRepository extends JpaRepository<Status,String> {
    public Status findByStatusId(Integer id);
    public List<Status> findByUserUserIdAndDateOrderByStatusId(Integer userId, Date date);
    public Integer countByUserUserIdAndDateGreaterThanEqual(Integer userId, Date date);
    public List<Status> findByUserUserIdOrderByDate(Integer userId);
    public void deleteByStatusId(Integer statusId);
    public List<Status> findByUserUserId(Integer userId);

    public Boolean existsByUserUserIdAndStatusId(Integer userId, Integer statusId);
}
