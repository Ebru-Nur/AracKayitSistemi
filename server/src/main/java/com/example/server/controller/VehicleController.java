package com.example.server.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.server.exception.ResourceNotFoundException;
import com.example.server.model.Vehicle;
import com.example.server.repository.VehicleRepository;

@RestController
@RequestMapping("/api/v1/")
public class VehicleController {
    
    @Autowired
    private VehicleRepository vehicleRepository;

    //tüm araçları getirir
    @GetMapping("/vehicles")
    public List <Vehicle> getAllVehicles(){
        return vehicleRepository.findAll();
    }

    //delete vehicle rest api
    @DeleteMapping("/vehicles")
    public ResponseEntity <Map<String,Boolean>> deleteVehicle(@RequestBody Long id){
          // Kimlik doğrulama ve kullanıcı yetkilendirmesi sağlandıktan sonra devam eder
    if (!isAuthorizedToDelete(id)) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Collections.singletonMap("error", Boolean.TRUE));
    }
        Vehicle vehicle= vehicleRepository.findById(id)
        .orElseThrow(()-> new ResourceNotFoundException("Araç bulunamadı."));

        vehicleRepository.delete(vehicle);
        Map<String,Boolean> response = new HashMap<>();
        response.put("Araç silindi",Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
    private boolean isAuthorizedToDelete(Long vehicleId){
        return true;
    }
}
