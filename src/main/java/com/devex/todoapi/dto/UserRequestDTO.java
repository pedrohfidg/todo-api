package com.devex.todoapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRequestDTO {

    @NotBlank(message = "Campo obrigatório")
    @Size(min = 3, max = 80, message = "Campo nome precisa ter no mínimo 3 caracteres e no máximo 80 caracteres")
    private String nome;

    @NotBlank(message = "Campo requirido")
    @Email(message = "Email fornecido não é válido")
    private String email;

    @NotBlank(message = "Campo requirido")
    @Size(min = 6, message = "Campo password precisa ter no mínimo 6 caracteres")
    private String password;

    public UserRequestDTO() {

    }

    public UserRequestDTO(String nome, String email, String password) {
        this.nome = nome;
        this.email = email;
        this.password = password;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
