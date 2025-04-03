package com.work.motel.infraestructure.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.work.motel.application.service.QuartoService;
import com.work.motel.domain.entity.Quarto;

@RequestMapping("/room")
@RestController
public class QuartoController {

  @Autowired
  private QuartoService service;  // Injeção de dependência diretamente no campo
  
  @GetMapping
  public ResponseEntity<List<Quarto>> getAllRooms() {
    List<Quarto> response = service.getAll();
    return ResponseEntity.ok(response);
  }
}
