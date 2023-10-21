package com.example.server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.server.repository.UserRepository;
import com.example.server.exception.ResourceNotFoundException;
import com.example.server.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RestController
@RequestMapping("/api/v1/")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // get all users
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // endpoint == api
    // register user
    @PostMapping("/register")
    public User registerUsers(@RequestBody User user) {// kullanıcı verileri @RequestBody kullanılarak alınır.
        User foundedUser;
        System.out.println(user.getPassword());
        try {
            foundedUser = userRepository.findByUsername(user.getUsername());
            if (foundedUser != null) {
                throw new Exception("Bu kullanıcı adı daha önceden alınmış.");
            } else {
                user.setPassword(encodePassword(user.getPassword()));
                //System.out.println(user.getPassword());
                foundedUser=userRepository.save(user);
                foundedUser.setToken(encodePassword(String.valueOf(foundedUser.getId())));
                foundedUser.setId(-1);
                return foundedUser;
            }
        } catch (Exception e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @PostMapping("/login")
    public User loginUser(@RequestBody User user) {
        User foundedUser;
        try {
            foundedUser = userRepository.findByUsername(user.getUsername());
        } catch (Exception e) {
            throw new ResourceNotFoundException("Kullanıcı bulunamadı: ");
        }
        if (checkPassword(user.getPassword(), foundedUser.getPassword())) {
            return foundedUser;
        } else {
            throw new ResourceNotFoundException("Şifre hatalı!");
        }
    }

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}