package com.example.spring_ai_demo.controller;

import com.example.spring_ai_demo.model.Plant;
import com.example.spring_ai_demo.service.PlantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/plants")
@CrossOrigin(origins = "*")
public class PlantController {

    private final PlantService plantService;
    private static String UPLOADED_FOLDER = "src/main/resources/static/";

    @Autowired
    public PlantController(PlantService plantService) {
        this.plantService = plantService;
    }

    @GetMapping
    public List<Plant> getAllPlants() {
        return plantService.getAllPlants();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Plant> getPlantById(@PathVariable Long id) {
        Optional<Plant> plant = plantService.getPlantById(id);
        return plant.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/type/{type}")
    public List<Plant> getPlantsByType(@PathVariable String type) {
        return plantService.getPlantsByType(type);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlant(@PathVariable Long id) {
        plantService.deletePlant(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<String> addPlant(@RequestParam("name") String name,
                           @RequestParam("type") String type,
                           @RequestParam("info") String info,
                           @RequestParam("image") MultipartFile image,
                           Model model) throws IOException {

        // Save the image file to the server
        if (!image.isEmpty()) {
            String imageName = image.getOriginalFilename();
            Path path = Paths.get(UPLOADED_FOLDER + imageName);
            image.transferTo(path);

            // Create the plant object
            Plant newPlant = new Plant();
            newPlant.setName(name);
            newPlant.setType(type);
            newPlant.setInfo(info);
            newPlant.setImage(imageName);

            plantService.addPlant(newPlant);
            return ResponseEntity.ok("Plant added successfully!");
        } else {
            return ResponseEntity.badRequest().body("Image file is required!");
        }
    }
}
