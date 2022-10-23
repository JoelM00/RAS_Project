package br.com.danielpadua.java_spring_idea_example.controllers;

import bd.Objects.Aposta;
import bd.Objects.ApostaUtilizador;
import bd.Objects.Jogo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import bd.SSJogo;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ExampleController
 *
 * @author danielpadua
 *
 */
@RestController
@RequestMapping("/api/example")
public class ExampleController {

    SSJogo ssj = new SSJogo();
    List<ApostaUtilizador> apostas = ssj.verHistoricoApostas("Dinis");
    List<Jogo> maisApostados = ssj.maisApostados();

    @CrossOrigin
    @GetMapping("/hello-world")
    public ResponseEntity<String> get() {
        return ResponseEntity.ok("Hello World!");
    }

    @CrossOrigin
    @GetMapping("/jogo/verHistoricoApostas")
    public List<ApostaUtilizador> verHistoricoApostas() {
        return apostas;
    }

    @CrossOrigin
    @GetMapping("/jogo/maisApostados")
    public List<Jogo> maisApostados() {
        return maisApostados;
    }

    @CrossOrigin
    @GetMapping("/jogo/todosJogos")
    public List<Jogo> todosJogos() {
        return ssj.todosJogos();
    }

    @CrossOrigin
    @GetMapping("/jogo/getApostas")
    public List<Aposta> getApostas(@RequestParam(value = "id", defaultValue = "1") String id) {
        return ssj.getApostas(Integer.parseInt(id));
    }


}
