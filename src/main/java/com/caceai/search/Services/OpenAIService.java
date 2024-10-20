package com.caceai.search.Services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class OpenAIService {

    private final String API_URL = "https://api.openai.com/v1/chat/completions"; // URL da API do ChatGPT

    @Value("${OPENAI_API_KEY}")
    private String API_KEY; // Chave da API

    public String obterResposta(String consulta) {
        RestTemplate restTemplate = new RestTemplate();

        // Configura o cabeçalho com a chave da API
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + API_KEY);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Cria o corpo da requisição com a consulta
        String requestBody = createRequestBody(consulta);

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        // Faz a chamada à API
        ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.POST, request, String.class);

        return processResponse(response);
    }

    private String createRequestBody(String consulta) {
        return "{"
                + "\"model\": \"gpt-3.5-turbo\"," // Modelo que você quer usar
                + "\"messages\":[{\"role\":\"user\",\"content\":\"" + consulta + "\"}]"
                + "}";
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
