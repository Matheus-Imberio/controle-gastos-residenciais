package com.despesas.despesas.services;

import com.despesas.despesas.entities.Pessoa;
import com.despesas.despesas.entities.Transacao;
import com.despesas.despesas.repositories.PessoaRepository;
import com.despesas.despesas.repositories.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private TransacaoRepository transacaoRepository;

    public List<Pessoa> findAll() {
        return pessoaRepository.findAll();
    }

    public Optional<Pessoa> findById(Long id) {
        return pessoaRepository.findById(id);
    }

    public Pessoa save(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

    public void deleteById(Long id) {
        pessoaRepository.deleteById(id);
    }

    public Map<String, BigDecimal> getTotaisPorPessoa(Long pessoaId) {
        List<Transacao> transacoes = transacaoRepository.findByPessoaId(pessoaId);
        BigDecimal totalReceitas = transacoes.stream()
                .filter(t -> t.getTipo().equals("receita"))
                .map(Transacao::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalDespesas = transacoes.stream()
                .filter(t -> t.getTipo().equals("despesa"))
                .map(Transacao::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal saldo = totalReceitas.subtract(totalDespesas);

        Map<String, BigDecimal> totais = new HashMap<>();
        totais.put("totalReceitas", totalReceitas);
        totais.put("totalDespesas", totalDespesas);
        totais.put("saldo", saldo);

        return totais;
    }

    public Map<String, BigDecimal> getTotaisGerais() {
        List<Pessoa> pessoas = pessoaRepository.findAll();
        BigDecimal totalReceitas = BigDecimal.ZERO;
        BigDecimal totalDespesas = BigDecimal.ZERO;

        for (Pessoa pessoa : pessoas) {
            Map<String, BigDecimal> totaisPessoa = getTotaisPorPessoa(pessoa.getId());
            totalReceitas = totalReceitas.add(totaisPessoa.get("totalReceitas"));
            totalDespesas = totalDespesas.add(totaisPessoa.get("totalDespesas"));
        }

        BigDecimal saldoLiquido = totalReceitas.subtract(totalDespesas);

        Map<String, BigDecimal> totaisGerais = new HashMap<>();
        totaisGerais.put("totalReceitas", totalReceitas);
        totaisGerais.put("totalDespesas", totalDespesas);
        totaisGerais.put("saldoLiquido", saldoLiquido);

        return totaisGerais;
    }
}