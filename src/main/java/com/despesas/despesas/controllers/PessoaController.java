package com.despesas.despesas.controllers;

import com.despesas.despesas.entities.Pessoa;
import com.despesas.despesas.services.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @GetMapping
    public List<Pessoa> findAll() {
        return pessoaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> findById(@PathVariable Long id) {
        return pessoaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Pessoa save(@RequestBody Pessoa pessoa) {
        return pessoaService.save(pessoa);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        pessoaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}/totais")
    public ResponseEntity<Map<String, BigDecimal>> getTotaisPorPessoa(@PathVariable Long id) {
        return ResponseEntity.ok(pessoaService.getTotaisPorPessoa(id));
    }

    @GetMapping("/totais-gerais")
    public ResponseEntity<Map<String, BigDecimal>> getTotaisGerais() {
        return ResponseEntity.ok(pessoaService.getTotaisGerais());
    }
}