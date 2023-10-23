package com.example.server.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.server.exception.ResourceNotFoundException;
import com.example.server.model.User;
import com.example.server.model.Vehicle;
import com.example.server.repository.UserRepository;
import com.example.server.repository.VehicleRepository;

@RestController
@RequestMapping("/api/v1/")
public class VehicleController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    // tüm araçları getirir
    @GetMapping("/vehicles")
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    @GetMapping("/myVehicles")
    public ResponseEntity<List<Vehicle>> getMyVehicles(@RequestBody String token) {
        User foundedUser = null;
        List<Vehicle> foundedVehicles = null;
        try {
            List<User> allUsers = userRepository.findAll();
            for (User user : allUsers) {
                if (checkPassword(String.valueOf(user.getId()), token)) {
                    foundedUser = user;// aranan user bulunduysa founded user üzerinden işleme devam edilir
                    break;
                }
            }
            if (foundedUser == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            foundedVehicles = vehicleRepository.findByUserId(String.valueOf(foundedUser.getId()));
            return ResponseEntity.ok(foundedVehicles);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // araç ekleme api
    @PostMapping("/addVehicle")
    public ResponseEntity<String> addVehicle(@RequestBody Vehicle vehicle) {
        User foundedUser = null;
        Vehicle foundedVehicle = null;
        try {
            List<User> allUsers = userRepository.findAll();
            for (User user : allUsers) {
                if (checkPassword(String.valueOf(user.getId()), vehicle.getUserId())) {
                    foundedUser = user;// aranan user bulunduysa founded user üzerinden işleme devam edilir
                    break;
                }
            }
            if (foundedUser == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Kullanıcı bulunamadı.");
            }

            foundedVehicle = vehicleRepository.findByNumberPlate(vehicle.getNumberPlate());
            if (foundedVehicle != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Araç sistemde kayıtlı.");
            } else {
                if (isStringValid(vehicle.getNumberPlate())) {
                    vehicle.setUserId(String.valueOf(foundedUser.getId()));// veri tabanına gerçek user id kaydedildi

                    vehicleRepository.save(vehicle);
                    return ResponseEntity.status(HttpStatus.OK).body("Araç bilgileri kaydedildi.");
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("Plaka boşluk, küçük harf ya da Türkçe karakter içermemelidir.");
                }

            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Bilinmeyen bir hata oluştu.");
        }
    }

    // delete vehicle rest api
    @DeleteMapping("/vehicles")
    public ResponseEntity<Map<String, Boolean>> deleteVehicle(@RequestBody Long id) {
        // Kimlik doğrulama ve kullanıcı yetkilendirmesi sağlandıktan sonra devam eder
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Araç bulunamadı."));

        vehicleRepository.delete(vehicle);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Araç silindi", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    public static boolean isStringValid(String input) {
        // Türkçe karakterleri kontrol eden regex
        String regex = "^[A-Z0-9]*$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        // Eğer regex eşleşirse, String geçerli kabul edilir
        return matcher.matches();
    }

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
