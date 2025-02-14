package com.despesas.despesas.repositories;

import com.despesas.despesas.entities.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
    List<Transacao> findByPessoaId(Long pessoaId);
}