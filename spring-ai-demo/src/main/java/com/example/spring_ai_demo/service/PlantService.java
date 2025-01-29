package com.example.spring_ai_demo.service;

import com.example.spring_ai_demo.model.Plant;
import com.example.spring_ai_demo.repository.PlantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlantService {

    private final PlantRepository plantRepository;

    @Autowired
    public PlantService(PlantRepository plantRepository) {
        this.plantRepository = plantRepository;
    }

    public List<Plant> getAllPlants() {
        return plantRepository.findAll();
    }

    public Optional<Plant> getPlantById(Long id) {
        return plantRepository.findById(id);
    }

    public List<Plant> getPlantsByType(String type) {
        return plantRepository.findByType(type);
    }

    public Plant getPlantsByName(String name) {
        return plantRepository.findByName(name);
    }

    public Plant addPlant(Plant plant) {
        return plantRepository.save(plant);
    }

    public void deletePlant(Long id) {
        plantRepository.deleteById(id);
    }
}
