package org.example.repository;

import jakarta.transaction.Transactional;
import org.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User,String> {
    User findByEmail(String email);
    Boolean existsByEmail(String email);
    User findByUserId(Integer userId);

}
