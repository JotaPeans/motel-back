package com.work.motel.application.service;

import java.util.List;

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

  public Quarto getById() {
    return this.repository.getById();
  }

  public Quarto create() {
    return this.repository.create();
  }

  public Quarto update() {
    return this.repository.update();
  }

  public void delete() {
    this.repository.delete();
  }

}
