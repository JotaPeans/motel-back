package com.work.motel.presentation.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.work.motel.application.service.ReservaService;
import com.work.motel.domain.entities.Reserva;

@RequestMapping("/reserva")
@RestController
public class ReservaController extends PrivateController {

    @Autowired
    private ReservaService service;

    @GetMapping
    public ResponseEntity<List<Reserva>> getAllReservas(
      @RequestParam(defaultValue = "1") int page, 
      @RequestParam(defaultValue = "10") int size
    ) {
      List<Reserva> response = service.getAll(page, size);
      return ResponseEntity.ok(response);
    }

    @GetMapping("/room/{id}")
    public ResponseEntity<List<Reserva>> getReservasByRoomId(@PathVariable Integer id) {
      List<Reserva> response = service.getReservasByRoomId(id);
      return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Optional<Reserva>> createReserva(@RequestBody Reserva data) {
      Optional<Reserva> response = service.create(Optional.of(data));
      return ResponseEntity.ok(response);
    }

    @PatchMapping("/checkout/{id}")
    public ResponseEntity<Void> checkoutReserva(@PathVariable Integer id) {
      service.checkout(id);
      return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Optional<Reserva>> updateReserva(@PathVariable Integer id, @RequestBody Optional<Reserva> data) {
      Optional<Reserva> response = service.update(id, data);
      return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Reserva>> getReservaById(@PathVariable Integer id) {
      Optional<Reserva> response = service.getReservaById(id);
      return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReserva(@PathVariable Integer id) {
      service.delete(id);
      return ResponseEntity.ok().build();
    }
}
