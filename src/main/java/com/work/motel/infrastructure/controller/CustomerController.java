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

import com.work.motel.application.service.CustomerService;
import com.work.motel.domain.entity.Customer;

@RequestMapping("/customer")
@RestController
public class CustomerController {

  @Autowired
  private CustomerService service;  // Injeção de dependência diretamente no campo

  @GetMapping
  public ResponseEntity<List<Customer>> getCustomers(@RequestParam(required = false) String nome) {
    List<Customer> response = service.getAll(nome);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/room/{id}")
  public ResponseEntity<List<Customer>> getCustomerByRoomId(@PathVariable Integer id) {
    List<Customer> response = service.getCustomerByRoomId(id);
    return ResponseEntity.ok(response);
  }

  @PostMapping
  public ResponseEntity<Optional<Customer>> createCustomer(@RequestBody Customer data) {
    Optional<Customer> response = service.create(Optional.of(data));
    return ResponseEntity.ok(response);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Optional<Customer>> updateCustomer(@PathVariable Integer id, @RequestBody Optional<Customer> data) {
    Optional<Customer> response = service.update(id, data);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Optional<Customer>> getCustomerById(@PathVariable Integer id) {
    Optional<Customer> response = service.getById(id);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Optional<Customer>> deleteCustomer(@PathVariable Integer id) {
    service.delete(id);
    return ResponseEntity.ok(null);
  }

}
