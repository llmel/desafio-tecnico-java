package com.leonardo.api_gerenciador_de_tarefas.repository;

import com.leonardo.api_gerenciador_de_tarefas.entity.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TarefasRepository extends JpaRepository<Tarefa, Integer> {

}
