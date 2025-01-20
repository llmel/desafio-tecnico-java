package com.leonardo.api_gerenciador_de_tarefas.controller;

import com.leonardo.api_gerenciador_de_tarefas.entity.Tarefa;
import com.leonardo.api_gerenciador_de_tarefas.entity.TarefaStatus;
import com.leonardo.api_gerenciador_de_tarefas.repository.TarefaStatusRepository;
import com.leonardo.api_gerenciador_de_tarefas.repository.TarefasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/tarefas")
public class TarefasController {

    @Autowired
    private TarefasRepository repository;
    @Autowired
    private TarefaStatusRepository tarefaStatusRepository;

    @GetMapping("/todas")
    public ResponseEntity BuscarTodasTarefas () {
        try {
            List tarefas = repository.findAll();

            return tarefas.isEmpty() ? noContent().build() : ok(tarefas);
        } catch (Exception erro) {
            return JuntarStatusHttpEMensagem(500, erro.getMessage());
        }
    }

    @GetMapping("{id}")
    public ResponseEntity BuscarTarefaPorId(
            @PathVariable int id
    ) {
        try {
            Optional<Tarefa> tarefa = repository.findById(id);

            if (tarefa.equals(Optional.empty())) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok(tarefa);
            }
        } catch (Exception erro) {
            return JuntarStatusHttpEMensagem(500, erro.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity CriarTarefa(@RequestBody Tarefa novaTarefa) {
        try {
            if (StatusExiste(novaTarefa)) {
                TarefaStatus status =
                        tarefaStatusRepository.findById(novaTarefa.getStatus().getIdStatus())
                                .orElseThrow(() -> new RuntimeException("Status não encontrado"));
                novaTarefa.setStatus(status);
            }

            novaTarefa.setDataCriacao(new Date());
            this.repository.save(novaTarefa);

            return ResponseEntity.created(null).build();
        } catch (RuntimeException erro) {
            return JuntarStatusHttpEMensagem(400, erro.getMessage());
        } catch (Exception erro) {
            return JuntarStatusHttpEMensagem(500, erro.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity EditarTarefa(
            @PathVariable Integer id,
            @RequestBody Tarefa tarefaEditada
    ) {
        try {
            Optional<Tarefa> tarefaExiste = this.repository.findById(id);

            if (tarefaExiste.isPresent()) {
                Tarefa tarefaAtual = this.repository.findById(id).get();

                tarefaAtual.setTitulo(tarefaEditada.getTitulo());
                tarefaAtual.setDescricao(tarefaEditada.getDescricao());

                if (StatusExiste(tarefaEditada)) {
                    TarefaStatus status =
                            tarefaStatusRepository.findById(tarefaEditada.getStatus().getIdStatus())
                                    .orElseThrow(() -> new RuntimeException("Status não encontrado"));
                    tarefaAtual.setStatus(status);
                }

                this.repository.save(tarefaAtual);

                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (RuntimeException erro) {
            return JuntarStatusHttpEMensagem(400, erro.getMessage());
        } catch (Exception erro) {
            return JuntarStatusHttpEMensagem(500, erro.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity ExcluirTarefa (@PathVariable int id) {
        try {
            if (repository.existsById(id)) {
                repository.deleteById(id);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception erro) {
            return JuntarStatusHttpEMensagem(500, erro.getMessage());
        }
    }

    public boolean StatusExiste(Tarefa tarefa) {
        return (tarefa.getStatus() != null && tarefa.getStatus().getIdStatus() != null);
    }

    public ResponseEntity JuntarStatusHttpEMensagem(int statusHttp, String msg) {
        switch (statusHttp) {
            case 400:
                return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
            case 500:
                return new ResponseEntity<>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
            default:
                return internalServerError().build();
        }
    }

}
