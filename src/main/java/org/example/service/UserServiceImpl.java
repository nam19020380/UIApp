package org.example.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.entity.User;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Transactional(rollbackOn = Exception.class)
    public Integer saveUser(User user) {
        String encodePass = encoder.encode(user.getPassword());
        user.setPassword(encodePass);
        return userRepository.save(user).getUserId();
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findUserById(Integer id) {
        return userRepository.findByUserId(id);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }
}
