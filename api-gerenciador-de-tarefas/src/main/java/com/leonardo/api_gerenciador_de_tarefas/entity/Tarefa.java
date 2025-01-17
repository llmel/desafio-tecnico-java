package com.leonardo.api_gerenciador_de_tarefas.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TBL_TAREFAS")
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TAREFA")
    private Integer idTarefa;

    @Column(name = "TITULO", nullable = false, length = 40)
    private String titulo;

    @Column(name = "DESCRICAO", nullable = false, length = 400)
    private String descricao;

    @Column(name = "DATA_CRIACAO", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCriacao;

    @ManyToOne
    @JoinColumn(name = "ID_STATUS", nullable = false)
    private TarefaStatus status;

    // Getters e Setters
    public Integer getIdTarefa() {
        return idTarefa;
    }

    public void setIdTarefa(Integer idTarefa) {
        this.idTarefa = idTarefa;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public TarefaStatus getStatus() {
        return status;
    }

    public void setStatus(TarefaStatus status) {
        this.status = status;
    }
}

