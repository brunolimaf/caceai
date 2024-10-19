package com.caceai.search.controllers;

 // Verifique se o pacote está correto
import com.caceai.search.Services.OpenAIService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedList;
import java.util.List;

@Controller
public class SearchController {

    private final List<String> respostas = new LinkedList<>();


    @Autowired
    private OpenAIService openAIService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("respostas", respostas);
        return "index";
    }

    @PostMapping("/")
    @ResponseBody
    public String search(@RequestParam("consulta") String consulta, HttpServletRequest request) {
        String resposta = openAIService.obterResposta(consulta);
        respostas.add(0, resposta); // Adiciona a resposta no início da lista

        // Verifica se a requisição é AJAX
        if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            // Se for AJAX, retorna a resposta diretamente
            return resposta;
        } else {
            // Para requisições normais, retorne um erro ou ajuste conforme sua necessidade
            return "Acesso não permitido";
        }
    }


}
