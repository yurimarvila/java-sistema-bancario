package br.com.banco.sistema_bancario.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.banco.sistema_bancario.model.Conta;
import br.com.banco.sistema_bancario.model.Transacao;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
    List<Transacao> findByConta(Conta conta);
}
