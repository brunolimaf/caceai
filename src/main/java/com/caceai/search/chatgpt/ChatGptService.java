package com.caceai.search.chatgpt;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class ChatGptService {
    private static final String API_URL = "https://api.openai.com/v1/chat/completions"; // URL da API
    private static final String API_KEY = "sua_api_key_aqui"; // Substitua pela sua chave de API

    public static void main(String[] args) {
        ChatGptResponse response = new ChatGptResponse();
        response.setText("Hello, how can I help you?");
        response.setTag("greeting");

        String json = response.toJson();

        // Aqui você pode fazer a chamada para a API do ChatGPT
        enviarParaChatGpt(json);
    }

    private static void enviarParaChatGpt(String json) {
        try {
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "Bearer " + API_KEY);
            conn.setDoOutput(true);

            // Enviando o JSON
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = json.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Lendo a resposta
            int responseCode = conn.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                StringBuilder responseBuilder = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    responseBuilder.append(responseLine.trim());
                }
                System.out.println("Response: " + responseBuilder.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
