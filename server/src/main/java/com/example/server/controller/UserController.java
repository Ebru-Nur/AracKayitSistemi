package com.example.server.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
                // System.out.println(user.getPassword());
                foundedUser = userRepository.save(user);
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
            foundedUser.setToken(encodePassword(String.valueOf(foundedUser.getId())));
            foundedUser.setId(-1);
            return foundedUser;
        } else {
            throw new ResourceNotFoundException("Şifre hatalı!");
        }
    }

    // şifre değiştirme api
    @PutMapping("/newPass")
    public ResponseEntity<String> changePassword(@RequestBody String[] userInfo) {
        User foundedUser = null;
        // userInfo[0]=token;
        // userInfo[1]=old Password;
        // userInfo[2]=new Password;
        try {
            List<User> allUsers = userRepository.findAll();
            for (User user : allUsers) {
                if (checkPassword(String.valueOf(user.getId()), userInfo[0])) {
                    foundedUser = user;// aranan user bulunduysa founded user üzerinden işleme devam edilir
                    break;
                }
            }
            if (foundedUser == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Kullanıcı bulunamadı.");
            }
            if (checkPassword(userInfo[2], foundedUser.getPassword())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Yeni şifre,eski şifreden farklı olmalıdır.");
            } else {
                foundedUser.setPassword(encodePassword(userInfo[2]));
                userRepository.save(foundedUser);
                return ResponseEntity.status(HttpStatus.OK).body("Şifre değiştirildi");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Bilinmeyen bir hata oluştu.");
        }
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<String> deleteUser(@RequestBody String token) {
        User foundedUser = null;
        try {
            List<User> allUsers = userRepository.findAll();
            for (User user : allUsers) {
                if (checkPassword(String.valueOf(user.getId()),token)) {
                    foundedUser = user;// aranan user bulunduysa founded user üzerinden işleme devam edilir
                    break;
                }
            }
            if (foundedUser == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Kullanıcı bulunamadı.");
            }
            else{
                userRepository.delete(foundedUser);
                return ResponseEntity.status(HttpStatus.OK).body("Kullanıcı başarıyla silindi.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Bilinmeyen bir hata oluştu.");
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