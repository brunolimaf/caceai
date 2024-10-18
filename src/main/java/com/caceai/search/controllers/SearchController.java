package com.caceai.search.controllers;

import com.caceai.search.Services.HuggingFaceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedList;
import java.util.List;

@Controller
public class SearchController {

    private final HuggingFaceService huggingFaceService;
    private final List<String> respostas = new LinkedList<>();

    public SearchController(HuggingFaceService huggingFaceService) {
        this.huggingFaceService = huggingFaceService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("respostas", respostas);
        return "index";
    }

    @PostMapping("/")
    public String pesquisar(@RequestParam("consulta") String consulta, Model model) {
        String resposta = huggingFaceService.obterResposta(consulta);
        respostas.add(0, resposta); // Adiciona a resposta no início da lista
        model.addAttribute("respostas", respostas);
        return "index";
    }
}
