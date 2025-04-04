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
import org.springframework.web.bind.annotation.RestController;

import com.work.motel.application.service.ManagerService;
import com.work.motel.domain.entity.Manager;

@RequestMapping("/manager")
@RestController
public class ManagerController {

    @Autowired
    private ManagerService service;  // Injeção de dependência diretamente no campo

    @GetMapping
    public ResponseEntity<List<Manager>> getManagers() {
        List<Manager> response = service.getAll();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Optional<Manager>> createManager(@RequestBody Manager data) {
        Optional<Manager> response = service.create(Optional.of(data));
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Optional<Manager>> updateManager(@PathVariable Integer id, @RequestBody Optional<Manager> data) {
        Optional<Manager> response = service.update(id, data);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Manager>> getManagerById(@PathVariable Integer id) {
        Optional<Manager> response = service.getById(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteManager(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
