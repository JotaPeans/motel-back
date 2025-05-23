package com.work.motel.presentation.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.work.motel.application.service.ProductsService;
import com.work.motel.domain.entities.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductsController extends PrivateController {

    @Autowired
    private ProductsService service;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> response = service.getAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Product>> getProductById(@PathVariable Integer id) {
        Optional<Product> response = service.getById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody Optional<Product> data) {
        Optional<Product> response = service.create(data);
        if (response == null) {
            Map<String, String> erro = new HashMap<>();
            erro.put("mensagem", "Voce deve enviar nome, tipo, valor e custo do produto");
            erro.put("codigo", "400");
            return ResponseEntity.badRequest().body(erro);
        }
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Integer id, @RequestBody Optional<Product> data) {
        Optional<Product> response = service.update(id, data);
        if (response == null) {
            Map<String, String> erro = new HashMap<>();
            erro.put("mensagem", "Voce deve enviar nome, tipo, valor e custo do produto");
            erro.put("codigo", "400");
            return ResponseEntity.badRequest().body(erro);
        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok(null);
    }
}
