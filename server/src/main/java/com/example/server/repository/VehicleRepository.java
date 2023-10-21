package com.example.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.server.model.Vehicle;

@Repository
public interface VehicleRepository extends JpaRepository <Vehicle,Long>{
    
}
