package com.work.motel.presentation.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

import com.work.motel.application.service.QuartoService;
import com.work.motel.domain.entities.Quarto;

@RequestMapping("/room")
@RestController
public class QuartoController extends PrivateController {

  @Autowired
  private QuartoService service;  // Injeção de dependência diretamente no campo
  
  @GetMapping
  public ResponseEntity<List<Quarto>> getAllRooms() {
    List<Quarto> response = service.getAll();
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Optional<Quarto>> getRoomByid(@PathVariable Integer id) {
    Optional<Quarto> response = service.getById(id);
    return ResponseEntity.ok(response);
  }

  @PostMapping
  public ResponseEntity<?> createRoom(@RequestBody Optional<Quarto> data) {
    Optional<Quarto> response = service.create(data);
    
    if(response == null) {
      Map<String, String> erro = new HashMap<>();
      erro.put("mensagem", "Voce deve enviar ao menos o numero do quarto");
      erro.put("codigo", "400");
      return ResponseEntity.badRequest().body(erro);
    }
    return ResponseEntity.ok(response);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateRoom(@PathVariable Integer id, @RequestBody Optional<Quarto> data) {
    Optional<Quarto> response = service.update(id, data);
    
    if(response == null) {
      Map<String, String> erro = new HashMap<>();
      erro.put("mensagem", "Voce deve enviar ao menos o numero do quarto");
      erro.put("codigo", "400");
      return ResponseEntity.badRequest().body(erro);
    }
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteRoom(@PathVariable Integer id) {
    service.delete(id);
    
    return ResponseEntity.ok(null);
  }
}
