package com.work.motel.infrastructure.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.work.motel.application.service.CustomerService;
import com.work.motel.domain.entity.Customer;

@RequestMapping("/customer")
@RestController
public class CustomerController {

  @Autowired
  private CustomerService service;  // Injeção de dependência diretamente no campo

  @GetMapping("/")
  public ResponseEntity<List<Customer>> getCustomers() {
    List<Customer> response = service.getAll();
    return ResponseEntity.ok(response);
  }

  @GetMapping("/room/{id}")
  public ResponseEntity<List<Customer>> getCustomerByRoomId(@PathVariable Integer id) {
    List<Customer> response = service.getCustomerByRoomId(id);
    return ResponseEntity.ok(response);
  }

}
