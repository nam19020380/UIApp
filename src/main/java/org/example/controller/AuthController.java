package org.example.controller;

import jakarta.validation.Valid;
import org.example.entity.User;
import org.example.payload.request.ForgetPasswordRequest;
import org.example.payload.request.LoginRequest;
import org.example.payload.request.SignupRequest;
import org.example.payload.response.JwtResponse;
import org.example.repository.UserRepository;
import org.example.security.JwtUtils;
import org.example.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    ImageService imageService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

            User user = userRepository.findByEmail(loginRequest.getEmail());
            JwtResponse jwtResponse = new JwtResponse(jwtUtils.generateJwtToken(user.getEmail()),
                    user.getUserId(),
                    user.getUserName(),
                    user.getEmail());
            return ResponseEntity.ok().body(jwtResponse);
        } catch(Exception e){
            return ResponseEntity.badRequest()
                    .body("Incorrect password or email");
        }
    }

    @PostMapping(value = "/signup", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> registerUser(@Valid @ModelAttribute SignupRequest signUpRequest) {
        try{
            if (userRepository.existsByEmail(signUpRequest.getEmail())) {
                return ResponseEntity
                        .badRequest()
                        .body("Error: Email is already in use!");
            }

            // Create new user's account
            User user = new User(signUpRequest.getEmail(),
                    encoder.encode(signUpRequest.getPassword()),
                    signUpRequest.getUsername(),
                    signUpRequest.getBirthday());

            if(signUpRequest.getMultipartFile() != null){
                user.setUserAvatarlink(imageService.uploadImage(signUpRequest.getMultipartFile()));
            }
            user.setUserJob(signUpRequest.getJob());


            userRepository.save(user);

            return ResponseEntity.ok().body("User registered successfully!");
        }catch(Exception e){
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @PostMapping("/forgetP")
    public ResponseEntity<?> forgetPassword(@Valid @RequestBody ForgetPasswordRequest forgetPasswordRequest) {
        if(userRepository.existsByEmail(forgetPasswordRequest.getEmail())){
            User user = userRepository.findByEmail(forgetPasswordRequest.getEmail());
            return ResponseEntity.ok(new JwtResponse(jwtUtils.generateJwtToken(user.getEmail()),
                    user.getUserId(),
                    user.getUserName(),
                    user.getEmail()));
        } else {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Username is not exist!");
        }
    }

    @PutMapping("/changeP")
    public ResponseEntity<?> changePassword(@Valid @RequestBody LoginRequest loginRequest) {
        try{
            User user = userRepository.findByEmail(loginRequest.getEmail());
            user.setPassword(loginRequest.getPassword());
            user.setPassword(encoder.encode(user.getPassword()));
            userRepository.save(user);
            return ResponseEntity.ok().body("Password change successfully!");
        } catch (Exception e){
            return ResponseEntity.badRequest()
                    .body("Server Error");
        }
    }
}