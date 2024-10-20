package com.caceai.search.Services;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class OpenAIService {

    private final String apiKey; // Chave da API

    private final String API_URL = "https://api.openai.com/v1/chat/completions"; // URL da API do ChatGPT

    public OpenAIService(@Value("${openai.api.key}") String apiKey) {
        this.apiKey = apiKey;
    }

    public String obterResposta(String consulta) {
        RestTemplate restTemplate = new RestTemplate();

        // Configura o cabeçalho com a chave da API
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey); // Utiliza a chave da API correta
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Cria o corpo da requisição com a consulta
        String requestBody = createRequestBody(consulta);

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        // Faz a chamada à API
        ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.POST, request, String.class);

        return processResponse(response);
    }

    private String createRequestBody(String consulta) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, Object> requestMap = new HashMap<>();
            requestMap.put("model", "gpt-3.5-turbo");

            // Criação do conteúdo da mensagem
            Map<String, String> message = new HashMap<>();
            message.put("role", "user");  // Define o papel como "user"
            message.put("content", consulta);  // Define a consulta como conteúdo

            // Adiciona a mensagem no array de mensagens
            requestMap.put("messages", Collections.singletonList(message));

            return objectMapper.writeValueAsString(requestMap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao criar o corpo da requisição", e);
        }
    }

    private String processResponse(ResponseEntity<String> response) {
        if (response.getStatusCode() == HttpStatus.OK) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonResponse = objectMapper.readTree(response.getBody());
                // Acessa o texto gerado da resposta JSON
                return jsonResponse.get("choices").get(0).get("message").get("content").asText();
            } catch (Exception e) {
                return "Erro ao processar a resposta da API: " + e.getMessage();
            }
        } else {
            return "Erro ao chamar a API do OpenAI: " + response.getStatusCode()
                    + " - " + response.getBody(); // Retorna erro em caso de falha
        }
    }
}
