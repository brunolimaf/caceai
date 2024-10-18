package com.caceai.search.Services; //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


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

@Service
public class HuggingFaceService {

    private final String API_URL = "https://api-inference.huggingface.co/models/gpt2"; // Substitua pelo modelo desejado
    private final String API_TOKEN = "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"; // Substitua pela sua chave de API

    public String obterResposta(String consulta) {
        RestTemplate restTemplate = new RestTemplate();

        // Prepare o cabeçalho com a chave da API
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + API_TOKEN);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Cria o corpo da requisição com instrução para resposta em português
        Map<String, String> body = new HashMap<>();
        body.put("inputs", "Resultado da pesquisa: " + consulta);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

        // Faz a chamada à API
        ResponseEntity<String> response = restTemplate.postForEntity(API_URL, request, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonResponse = objectMapper.readTree(response.getBody());
                // Acessa o texto gerado da resposta JSON
                return jsonResponse.get(0).get("generated_text").asText();
            } catch (Exception e) {
                return "Erro ao processar a resposta da API: " + e.getMessage();
            }
        } else {
            return "Erro ao chamar a API do Hugging Face: " + response.getStatusCode(); // Retorna erro em caso de falha
        }
    }
}
