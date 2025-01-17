package com.leonardo.api_gerenciador_de_tarefas.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "TBL_TAREFAS_STATUS")
public class TarefaStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_STATUS")
    private Integer idStatus;

    @Column(name = "NOME", nullable = false, length = 40)
    private String nome;

    // Getters e Setters
    public Integer getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(Integer idStatus) {
        this.idStatus = idStatus;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}

