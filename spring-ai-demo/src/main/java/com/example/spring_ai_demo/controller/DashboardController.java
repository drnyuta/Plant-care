package com.example.spring_ai_demo.controller;

import com.example.spring_ai_demo.model.Plant;
import com.example.spring_ai_demo.repository.PlantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class DashboardController {

    @Autowired
    private PlantRepository plantRepository;

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        List<Plant> plants = plantRepository.findAll();

        System.out.println(plants.size());
        model.addAttribute("plants", plants);
        return "dashboard";
    }

    @GetMapping("/dashboard/{plantName}")
    public String viewPlantCare(@PathVariable("plantName") String plantName, Model model) {
        Plant plant = plantRepository.findByName(plantName);

        if (plant != null) {
            model.addAttribute("plant", plant);
        } else {
            model.addAttribute("message", "Plant not found.");
        }

        return "plantCare";
    }

    @GetMapping("/add")
    public String showAddPlantForm() {
        return "add";
    }
}
