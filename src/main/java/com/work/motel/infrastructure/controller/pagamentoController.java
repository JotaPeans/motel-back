package com.work.motel.infrastructure.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.work.motel.application.service.PagamentoService;
import com.work.motel.domain.entity.Pagamento;
import com.work.motel.enums.FormaPagamento;

import jakarta.servlet.http.HttpServletRequest;

@RequestMapping("/payment")
@RestController
public class pagamentoController extends PrivateController {

    @Autowired
    private PagamentoService service; // Injeção de dependência diretamente no campo

    @GetMapping
    public ResponseEntity<List<Pagamento>> getPagamentos(
            HttpServletRequest request,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<Pagamento> response = service.getAll(page, size);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/provider")
    public String CreatePaymentFromProvider(@RequestBody String entity,
            @RequestParam(defaultValue = "PIX") FormaPagamento forma_pagamento) {

        if (forma_pagamento.toString().equals("PIX")) {
            System.out.println(forma_pagamento);
        }
        return entity;
    }

    @PostMapping
    public ResponseEntity<Optional<Pagamento>> createPagamento(@RequestBody Pagamento data) {
        Optional<Pagamento> response = service.create(Optional.of(data));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Pagamento>> getPagamentoById(@PathVariable Integer id) {
        Optional<Pagamento> response = service.getById(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Optional<Pagamento>> deletePagamento(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok(null);
    }

}
