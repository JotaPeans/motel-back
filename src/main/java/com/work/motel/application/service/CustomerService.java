package com.work.motel.application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.work.motel.domain.entity.Customer;
import com.work.motel.domain.repository.CustomerRepository;

@Service
public class CustomerService {
  private final CustomerRepository repository;

  @Autowired
  public CustomerService(CustomerRepository repository) {
    this.repository = repository;
  }

  public List<Customer> getCustomerByRoomId(Integer id) {
    return this.repository.getCustomerByRoomId(id);
  }

  public List<Customer> getAll() {
    return this.repository.getAll();
  }

  public Optional<Customer> getById(Integer id) {
    return this.repository.getById(id);
  }

  public Optional<Customer> create(Optional<Customer> data) {
    return this.repository.create(data);
  }

  public Optional<Customer> update(Integer id, Optional<Customer> data) {
    return this.repository.updateById(id, data);
  }

  public void delete(Integer id) {
    this.repository.deleteById(id);
  }
}
