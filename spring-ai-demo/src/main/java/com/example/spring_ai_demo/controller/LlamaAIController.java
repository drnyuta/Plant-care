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

    @GetMapping("/")
    public String showForm() {
        return "index"; // Thymeleaf template name
    }

    @PostMapping("/generate")
    public String generate(@RequestParam String prompt, Model model) {
        // Call the Llama AI service and get the response
        String response = llamaAIService.getLlamaAIResponse(prompt);

        // Add the response to the model to be displayed on the page
        model.addAttribute("response", response);
        return "index"; // Return the same page with the response displayed
    }
}