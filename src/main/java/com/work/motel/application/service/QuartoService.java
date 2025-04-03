package com.work.motel.application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.work.motel.domain.entity.Quarto;
import com.work.motel.domain.repository.QuartoRepository;

@Service
public class QuartoService {
  private final QuartoRepository repository;

  @Autowired
  public QuartoService(QuartoRepository repository) {
    this.repository = repository;
  }

  public List<Quarto> getAll() {
    return this.repository.getAll();
  }

  public Optional<Quarto> getById(Integer id) {
    return this.repository.getById(id);
  }

  public Optional<Quarto> create(Optional<Quarto> data) {
    return this.repository.create(data);
  }

  public Optional<Quarto> update(Integer id, Quarto data) {
    return this.repository.updateById(id, data);
  }

  public void delete(Integer id) {
    this.repository.deleteById(id);
  }

}
