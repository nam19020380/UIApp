package org.example.service;

import org.example.entity.User;

import java.util.List;

public interface UserService {
    public Integer saveUser(User user);

    public User findUserByEmail(String email);

    public User findUserById(Integer id);

    public List<User> findAll();
}
