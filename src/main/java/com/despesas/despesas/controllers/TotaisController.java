package com.despesas.despesas.controllers;

import com.despesas.despesas.entities.Pessoa;
import com.despesas.despesas.entities.Transacao;
import com.despesas.despesas.repositories.PessoaRepository;
import com.despesas.despesas.repositories.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/totais")
public class TotaisController {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private TransacaoRepository transacaoRepository;

    @GetMapping
    public Map<String, Object> getTotais() {
        List<Pessoa> pessoas = pessoaRepository.findAll();
        Map<String, Object> resultado = new HashMap<>();

        BigDecimal totalGeralReceitas = BigDecimal.ZERO;
        BigDecimal totalGeralDespesas = BigDecimal.ZERO;

        // Lista para armazenar os totais de cada pessoa
        List<Map<String, Object>> totaisPorPessoa = new java.util.ArrayList<>();

        for (Pessoa pessoa : pessoas) {
            List<Transacao> transacoes = transacaoRepository.findByPessoaId(pessoa.getId());

            BigDecimal totalReceitas = transacoes.stream()
                    .filter(t -> t.getTipo().equals("receita"))
                    .map(Transacao::getValor)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal totalDespesas = transacoes.stream()
                    .filter(t -> t.getTipo().equals("despesa"))
                    .map(Transacao::getValor)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal saldo = totalReceitas.subtract(totalDespesas);

            // Adiciona os totais da pessoa ao resultado
            Map<String, Object> totaisPessoa = new HashMap<>();
            totaisPessoa.put("pessoaId", pessoa.getId());
            totaisPessoa.put("nome", pessoa.getNome());
            totaisPessoa.put("totalReceitas", totalReceitas);
            totaisPessoa.put("totalDespesas", totalDespesas);
            totaisPessoa.put("saldo", saldo);

            totaisPorPessoa.add(totaisPessoa);

            // Soma aos totais gerais
            totalGeralReceitas = totalGeralReceitas.add(totalReceitas);
            totalGeralDespesas = totalGeralDespesas.add(totalDespesas);
        }

        BigDecimal saldoLiquidoGeral = totalGeralReceitas.subtract(totalGeralDespesas);

        // Adiciona os totais gerais ao resultado
        resultado.put("totaisPorPessoa", totaisPorPessoa);
        resultado.put("totalGeralReceitas", totalGeralReceitas);
        resultado.put("totalGeralDespesas", totalGeralDespesas);
        resultado.put("saldoLiquidoGeral", saldoLiquidoGeral);

        return resultado;
    }
}