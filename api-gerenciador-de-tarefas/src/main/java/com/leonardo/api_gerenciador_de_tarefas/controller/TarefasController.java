package com.leonardo.api_gerenciador_de_tarefas.controller;

import com.leonardo.api_gerenciador_de_tarefas.repository.TarefasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/tarefas")
public class TarefasController {

    @Autowired
    private TarefasRepository repository;

    @GetMapping("/todas")
    public ResponseEntity BuscarTodasTarefas () {
        List tarefas = repository.findAll();

        return tarefas.isEmpty() ? noContent().build() : ok(tarefas);
    }
}
