package com.despesas.despesas.services;

import com.despesas.despesas.entities.Pessoa;
import com.despesas.despesas.entities.Transacao;
import com.despesas.despesas.repositories.PessoaRepository;
import com.despesas.despesas.repositories.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransacaoService {

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    public List<Transacao> findAll() {
        return transacaoRepository.findAll();
    }

    public Optional<Transacao> findById(Long id) {
        return transacaoRepository.findById(id);
    }

    public Transacao save(Transacao transacao) {
        // Verifica se a pessoa associada à transação existe
        Optional<Pessoa> pessoa = pessoaRepository.findById(transacao.getPessoa().getId());
        if (pessoa.isPresent()) {
            // Verifica se a pessoa é menor de idade e se a transação é uma despesa
            if (pessoa.get().getIdade() < 18 && !transacao.getTipo().equals("despesa")) {
                throw new IllegalArgumentException("Menores de idade só podem ter despesas.");
            }
            return transacaoRepository.save(transacao);
        } else {
            throw new IllegalArgumentException("Pessoa não encontrada.");
        }
    }

    public List<Transacao> findByPessoaId(Long pessoaId) {
        return transacaoRepository.findByPessoaId(pessoaId);
    }
}