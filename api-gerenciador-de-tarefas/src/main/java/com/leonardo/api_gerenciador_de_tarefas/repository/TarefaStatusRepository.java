package com.leonardo.api_gerenciador_de_tarefas.repository;

import com.leonardo.api_gerenciador_de_tarefas.entity.TarefaStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TarefaStatusRepository extends JpaRepository<TarefaStatus, Integer> {

}
