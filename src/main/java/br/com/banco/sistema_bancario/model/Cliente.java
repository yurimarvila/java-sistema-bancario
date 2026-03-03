package br.com.banco.sistema_bancario.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

// Encapsolamento padrão getter e setter
@Entity // indica que a classe eh uma entidade
public class Cliente {

    @Id // indica que o id eh uma chave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // gera o id automaticamente pelo banco de dados

    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private String senha;

    //id
    public Long getId() { // getter para retornar o id do cliente
        return id;
    }

    public void setId(Long id) { // setter para receber o id do cliente
        this.id = id; // this é usado para referenciar o id do cliente na classe
    }

    // nome
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    // CPF
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    // Email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Senha
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
