package com.example.spring_ai_demo.repository;

import com.example.spring_ai_demo.model.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlantRepository extends JpaRepository<Plant, Long> {
    List<Plant> findByType(String type);
    Plant findByName(String name);
}