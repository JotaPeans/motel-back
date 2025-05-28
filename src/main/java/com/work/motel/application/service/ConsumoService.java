package com.work.motel.application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.work.motel.domain.entities.Consumo;
import com.work.motel.infrastructure.repositories.ConsumoRepository;

@Service
public class ConsumoService {

    @Autowired
    private ConsumoRepository consumoRepository;

    public List<Consumo> getAll(String year, String produto_id, int page, int size) {
        return consumoRepository.getAll(year, produto_id, page, size);
    }

    public Optional<Consumo> getConsumoById(Integer id) {
        return consumoRepository.getById(id);
    }

    public List<Consumo> getConsumosByClienteId(Integer clienteId) {
        return consumoRepository.getByClienteId(clienteId);
    }

    public Optional<Consumo> create(Optional<Consumo> data) {
        return consumoRepository.create(data);
    }

    public Optional<Consumo> update(Integer id, Optional<Consumo> data) {
        return consumoRepository.updateById(id, data);
    }

    public void delete(Integer id) {
        consumoRepository.deleteById(id);
    }
}
