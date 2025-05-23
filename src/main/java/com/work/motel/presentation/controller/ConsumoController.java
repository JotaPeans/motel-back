package com.work.motel.presentation.controller;

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

import com.work.motel.application.service.ConsumoService;
import com.work.motel.domain.entities.Consumo;

@RequestMapping("/consumo")
@RestController
public class ConsumoController extends PrivateController {

    @Autowired
    private ConsumoService service;

    @GetMapping
    public ResponseEntity<List<Consumo>> getAllConsumos(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<Consumo> response = service.getAll(page, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Consumo>> getConsumosByClienteId(@PathVariable Integer clienteId) {
        List<Consumo> response = service.getConsumosByClienteId(clienteId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Consumo>> getConsumoById(@PathVariable Integer id) {
        Optional<Consumo> response = service.getConsumoById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Optional<Consumo>> createConsumo(@RequestBody Consumo data) {
        Optional<Consumo> response = service.create(Optional.of(data));
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Optional<Consumo>> updateConsumo(
            @PathVariable Integer id,
            @RequestBody Optional<Consumo> data) {
        Optional<Consumo> response = service.update(id, data);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConsumo(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
