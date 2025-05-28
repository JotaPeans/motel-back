package com.work.motel.application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.work.motel.domain.entities.Reserva;
import com.work.motel.infrastructure.repositories.ReservaRepository;

@Service
public class ReservaService {

  @Autowired
  private ReservaRepository reservaRepository;

  public List<Reserva> getAll(String ano_reserva,
      String room_type, int page, int size) {
    return reservaRepository.getAll(ano_reserva, room_type, page, size);
  }

  public Optional<Reserva> getReservaById(Integer id) {
    return reservaRepository.getById(id);
  }

  public Optional<Reserva> create(Optional<Reserva> data) {
    return reservaRepository.create(data);
  }

  public void checkout(Integer id) {
    reservaRepository.checkout(id);
  }

  public Optional<Reserva> update(Integer id, Optional<Reserva> reserva) {
    return reservaRepository.updateById(id, reserva);
  }

  public List<Reserva> getReservasByRoomId(Integer roomId) {
    return reservaRepository.getByRoomId(roomId);
  }

  public void delete(Integer id) {
    reservaRepository.deleteById(id);
  }
}
