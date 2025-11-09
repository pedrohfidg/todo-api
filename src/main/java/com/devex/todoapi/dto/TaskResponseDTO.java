package com.devex.todoapi.dto;

import com.devex.todoapi.model.Task;
import com.devex.todoapi.model.TaskStatus;

import java.time.Instant;

public class TaskResponseDTO {

    private Long id;
    private String titulo;
    private String descricao;
    private TaskStatus status;
    private Instant dataCriacao;
    private Instant dataConclusao;

    public TaskResponseDTO() {

    }

    public TaskResponseDTO(Long id, String titulo, String descricao, TaskStatus status, Instant dataCriacao, Instant dataConclusao) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.status = status;
        this.dataCriacao = dataCriacao;
        this.dataConclusao = dataConclusao;
    }

    public TaskResponseDTO(Task entity) {
        this.id = entity.getId();
        this.titulo = entity.getTitulo();
        this.descricao = entity.getDescricao();
        this.status = entity.getStatus();
        this.dataCriacao = entity.getDataCriacao();
        this.dataConclusao = entity.getDataConclusao();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Instant getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Instant dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Instant getDataConclusao() {
        return dataConclusao;
    }

    public void setDataConclusao(Instant dataConclusao) {
        this.dataConclusao = dataConclusao;
    }
}
