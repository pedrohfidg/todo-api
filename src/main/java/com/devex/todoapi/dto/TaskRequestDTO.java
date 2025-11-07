package com.devex.todoapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TaskRequestDTO {

    @NotBlank(message = "Campo titulo não pode ser vazio")
    @Size(min = 3, message = "Campo titulo precisa ter no mínimo 3 caracteres")
    private String titulo;

    @Size(max = 255, message = "Campo descricao pode ter no máximo 255 caracteres")
    private String descricao;

    public TaskRequestDTO() {

    }

    public TaskRequestDTO(String titulo, String descricao) {
        this.titulo = titulo;
        this.descricao = descricao;
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
}
