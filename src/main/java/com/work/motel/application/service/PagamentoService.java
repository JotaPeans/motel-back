package com.work.motel.application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.work.motel.domain.entities.Pagamento;
import com.work.motel.infrastructure.repositories.PagamentoRepository;

@Service
public class PagamentoService {
  private final PagamentoRepository repository;

  @Autowired
  public PagamentoService(PagamentoRepository repository) {
    this.repository = repository;
  }

  public List<Pagamento> getAll(int page, int size) {
    return this.repository.getAll(page, size);
  }

  public Optional<Pagamento> getById(Integer id) {
    return this.repository.getById(id);
  }

  public void addReservation(Integer paymentId, Integer ReservationId) {
    this.repository.addReservation(paymentId, ReservationId);
  }

  public Optional<Pagamento> getByPaymentProviderId(String id) {
    return this.repository.getByPaymentProviderId(id);
  }

  public Optional<Pagamento> create(Optional<Pagamento> data) {
    return this.repository.create(data);
  }

  public void delete(Integer id) {
    this.repository.deleteById(id);
  }
}
