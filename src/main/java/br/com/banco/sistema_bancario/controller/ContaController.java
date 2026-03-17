package br.com.banco.sistema_bancario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.banco.sistema_bancario.dto.ContaDTO;
import br.com.banco.sistema_bancario.dto.OperacaoDTO;
import br.com.banco.sistema_bancario.dto.TransacaoExtratoDTO;
import br.com.banco.sistema_bancario.dto.TransferenciaDTO;
import br.com.banco.sistema_bancario.model.Conta;
import br.com.banco.sistema_bancario.service.ContaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/contas")

public class ContaController {
    @Autowired
    private ContaService service;

    // POST /contas — criar conta @PostMapping
    @PostMapping
    public ResponseEntity<Conta> criar(@RequestBody @Valid ContaDTO dto) {
        Conta conta = service.criar(dto);
        return ResponseEntity.status(201).body(conta);
    }

    // GET /contas/{id} — buscar por id @GetMapping
    @GetMapping("/{id}")
    public ResponseEntity<Conta> buscarPorId(@PathVariable Long id) {
        Conta conta = service.buscarPorId(id);
        if (conta == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(conta);
    }

    // POST /contas/{id}/depositar — depositar @PostMapping
    @PostMapping("/{id}/depositar")
    public ResponseEntity<Conta> depositar(@PathVariable Long id, @RequestBody OperacaoDTO dto) {
        Conta conta = service.depositar(id, dto.getValor());
        return ResponseEntity.ok(conta);
    }

    // POST /contas/{id}/sacar — sacar @PostMapping
    @PostMapping("/{id}/sacar")
    public ResponseEntity<Conta> sacar(@PathVariable Long id, @RequestBody OperacaoDTO dto) {
        Conta conta = service.sacar(id, dto.getValor());
        return ResponseEntity.ok(conta);
    }

    // POST /contas/transferir — transferir @PostMapping
    @PostMapping("/transferir")
    public ResponseEntity<Conta> transferir(@RequestBody TransferenciaDTO dto) {
        Conta conta = service.tranferir(dto.getContaOrigemId(), dto.getValor(), dto.getContaDestinoId());
        return ResponseEntity.ok(conta);
    }

    // GET /contas/{id}/extrato — extrato @GetMapping
    @GetMapping("/{id}/extrato")
    public ResponseEntity<List<TransacaoExtratoDTO>> extrato(@PathVariable Long id) {
        List<TransacaoExtratoDTO> extrato = service.extrato(id);
        return ResponseEntity.ok(extrato);
    }
}
