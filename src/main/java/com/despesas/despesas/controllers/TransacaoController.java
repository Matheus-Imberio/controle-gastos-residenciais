package com.despesas.despesas.controllers;

import com.despesas.despesas.entities.Transacao;
import com.despesas.despesas.services.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    @Autowired
    private TransacaoService transacaoService;

    @GetMapping
    public List<Transacao> findAll() {
        return transacaoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transacao> findById(@PathVariable Long id) {
        return transacaoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Transacao save(@RequestBody Transacao transacao) {
        return transacaoService.save(transacao);
    }

    @GetMapping("/pessoa/{pessoaId}")
    public List<Transacao> findByPessoaId(@PathVariable Long pessoaId) {
        return transacaoService.findByPessoaId(pessoaId);
    }
}
