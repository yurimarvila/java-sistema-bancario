package br.com.banco.sistema_bancario.dto;

import java.time.LocalDateTime;

import br.com.banco.sistema_bancario.model.TipoTransacao;

public class TransacaoExtratoDTO {
    private double valor;
    private String nomeCliente;
    private Long id;
    private TipoTransacao tipo;
    private LocalDateTime dataHora;
    private String numeroConta;

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nome) {
        this.nomeCliente = nome;
    }

    // Id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // tipo (Deposito, saque, tranferencia)
    public TipoTransacao getTipo() {
        return tipo;
    }

    public void setTipo(TipoTransacao tipo) {
        this.tipo = tipo;
    }

    // Valor
    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    // Data e hora
    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    // Numero da conta
    public String getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }
}
