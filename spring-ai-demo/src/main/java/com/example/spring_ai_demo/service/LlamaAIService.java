package com.example.spring_ai_demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;

@Service
public class LlamaAIService {

    @Value("${replicate.api.key}")
    private String replicateApiKey;

    private final RestTemplate restTemplate;

    public LlamaAIService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getLlamaAIResponse(String prompt) {
        String url = "https://api.replicate.com/v1/models/meta/meta-llama-3-70b-instruct/predictions";

        // Prepare headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + replicateApiKey);
        headers.set("Content-Type", "application/json");
        headers.set("Prefer", "wait");

        // Prepare request body as JSON
        String requestBody = String.format("{\n" +
                "  \"input\": {\n" +
                "    \"top_k\": 0,\n" +
                "    \"top_p\": 0.9,\n" +
                "    \"prompt\": \"%s\",\n" +
                "    \"max_tokens\": 512,\n" +
                "    \"min_tokens\": 0,\n" +
                "    \"temperature\": 0.6,\n" +
                "    \"system_prompt\": \"You are a helpful assistant\",\n" +
                "    \"length_penalty\": 1,\n" +
                "    \"stop_sequences\": \"<|end_of_text|>,<|eot_id|>\",\n" +
                "    \"prompt_template\": \"<|begin_of_text|><|start_header_id|>system<|end_header_id|>\\n\\nYou are a helpful assistant<|eot_id|><|start_header_id|>user<|end_header_id|>\\n\\n{prompt}<|eot_id|><|start_header_id|>assistant<|end_header_id|>\\n\\n\",\n" +
                "    \"presence_penalty\": 1.15,\n" +
                "    \"log_performance_metrics\": false\n" +
                "  }\n" +
                "}", prompt);

        // Create HttpEntity with request body and headers
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        // Make the POST request and get the response
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        // Parse the response JSON
        JSONObject responseObject = new JSONObject(response.getBody());

        // Extract the output array and concatenate the strings into a single response text
        StringBuilder responseText = new StringBuilder();
        for (Object part : responseObject.getJSONArray("output")) {
            responseText.append(part.toString());
        }

        // Format the response
        String formattedResponse = formatResponse(responseText.toString());

        // Return the formatted response
        return formattedResponse;
    }

    // Method to format the response text
    private String formatResponse(String response) {

        // Replace **bold** text formatting
        response = response.replaceAll("\\*\\*(.*?)\\*\\*", "<b>$1</b>");

        // Format bullet points and line breaks
        response = response.replaceAll("(?m)^\\d+\\. ", "<br/> <b>$0</b>");
        response = response.replaceAll("(?<=\\n)(\\d+\\.\\s)", "<br/><b>$1</b>");
        response = response.replaceAll("\\n", "<br/>");

        return response;
    }
}

