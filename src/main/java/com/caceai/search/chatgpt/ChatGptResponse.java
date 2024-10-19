package com.caceai.search.chatgpt; // substitua "seunome" pelo seu nome ou pelo nome do seu projeto

import com.google.gson.Gson;

public class ChatGptResponse {
    private String text;
    private String tag;

    // Construtor
    public ChatGptResponse() {}

    // Getters e Setters
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    // Método para converter o objeto em JSON
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
