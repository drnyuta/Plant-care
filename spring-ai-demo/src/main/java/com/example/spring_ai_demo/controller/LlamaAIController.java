package com.example.spring_ai_demo.controller;

import com.example.spring_ai_demo.service.LlamaAIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
public class LlamaAIController {

    private final LlamaAIService llamaAIService;

    @Autowired
    public LlamaAIController(LlamaAIService llamaAIService) {
        this.llamaAIService = llamaAIService;
    }

    @GetMapping("/advices")
    public String showForm() {
        return "advices"; // Thymeleaf template name
    }

    @PostMapping("/generate")
    public String generate(@RequestParam String prompt, Model model) {

        String response = llamaAIService.getLlamaAIResponse(prompt);

        // Add the response to the model to be displayed on the page
        model.addAttribute("response", response);
        return "advices"; // Return the same page with the response displayed
    }
}