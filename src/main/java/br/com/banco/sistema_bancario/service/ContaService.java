package br.com.banco.sistema_bancario.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.banco.sistema_bancario.dto.ContaDTO;
import br.com.banco.sistema_bancario.dto.TransacaoExtratoDTO;
import br.com.banco.sistema_bancario.exception.RecursoNaoEncontradoException;
import br.com.banco.sistema_bancario.model.Cliente;
import br.com.banco.sistema_bancario.model.Conta;
import br.com.banco.sistema_bancario.model.TipoTransacao;
import br.com.banco.sistema_bancario.model.Transacao;
import br.com.banco.sistema_bancario.repository.ClienteRepository;
import br.com.banco.sistema_bancario.repository.ContaRepository;
import br.com.banco.sistema_bancario.repository.TransacaoRepository;

@Service
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private TransacaoRepository transacaoRepository;

    public Conta criar(ContaDTO dto) {
        Cliente cliente = clienteRepository.findById(dto.getClienteId()).orElse(null);
        if (cliente == null) {
            throw new RecursoNaoEncontradoException("Cliente não encontrado"); // Lançando uma exceção
        }

        Conta conta = new Conta();
        conta.setCliente(cliente);
        conta.setSaldo(0);

        conta.setNumeroConta(String.valueOf(System.currentTimeMillis()));
        return contaRepository.save(conta);
    }

    public Conta buscarPorId(Long id) {
        return contaRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Conta não encontrada"));
    }

    public Conta depositar(Long contaId, Double valor) {
        Conta conta = contaRepository.findById(contaId).orElseThrow(() -> new RecursoNaoEncontradoException("Conta não encontrada"));
        double saldoAtual = conta.getSaldo();
        double novoSaldo = saldoAtual + valor;
        conta.setSaldo(novoSaldo);
        contaRepository.save(conta);
        registrarTransacao(conta, TipoTransacao.DEPOSITO, valor);
        return conta;
    }

    public Conta sacar(Long contaId, Double valor) {
        Conta conta = contaRepository.findById(contaId).orElseThrow(() -> new RecursoNaoEncontradoException("Conta não encontrada"));
        double saldoAtual = conta.getSaldo();
        if (saldoAtual < valor) {
            throw new RuntimeException("Saldo insuficiente");
        }
        double novoSaldo = saldoAtual - valor;
        conta.setSaldo(novoSaldo);
        contaRepository.save(conta);
        registrarTransacao(conta, TipoTransacao.SAQUE, valor);
        return conta;
    }

    public Conta tranferir(Long contaId, Double valor, Long contaDestinoId) {
        Conta contaOrigem = contaRepository.findById(contaId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Conta não encontrada"));
        Conta contaDestino = contaRepository.findById(contaDestinoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Conta não encontrada"));

        double saldoOrigem = contaOrigem.getSaldo();
        double novoSaldoOrigem = saldoOrigem - valor;
        contaOrigem.setSaldo(novoSaldoOrigem);
        contaRepository.save(contaOrigem);
        registrarTransacao(contaOrigem, TipoTransacao.TRANSFERENCIA, valor);

        double saldoDestino = contaDestino.getSaldo();
        double novoSaldoDestino = saldoDestino + valor;
        contaDestino.setSaldo(novoSaldoDestino);
        contaRepository.save(contaDestino);

        registrarTransacao(contaDestino, TipoTransacao.TRANSFERENCIA, valor);
        return contaOrigem;
    }

    private void registrarTransacao(Conta conta, TipoTransacao tipo, Double valor) {
        Transacao transacao = new Transacao();
        transacao.setConta(conta);
        transacao.setTipo(tipo);
        transacao.setValor(valor);
        LocalDateTime dataHoraAtual = LocalDateTime.now();
        transacao.setDataHora(dataHoraAtual);
        transacaoRepository.save(transacao);
    }

    public List<TransacaoExtratoDTO> extrato(Long contaId) {
        Conta conta = contaRepository.findById(contaId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Conta não encontrada"));

        List<Transacao> transacoes = transacaoRepository.findByConta(conta);

        return transacoes.stream()
                .map(t -> {
                    TransacaoExtratoDTO dto = new TransacaoExtratoDTO();
                    dto.setId(t.getId());
                    dto.setNomeCliente(t.getConta().getCliente().getNome());
                    dto.setNumeroConta(t.getConta().getNumeroConta());
                    dto.setTipo(t.getTipo());
                    dto.setValor(t.getValor());
                    dto.setDataHora(t.getDataHora());
                    return dto;
                })
                .toList();
    }
}