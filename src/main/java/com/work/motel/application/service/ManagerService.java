package com.work.motel.application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.work.motel.domain.entity.Manager;
import com.work.motel.domain.repository.ManagerRepository;

@Service
public class ManagerService {
    private final ManagerRepository repository;

    @Autowired
    public ManagerService(ManagerRepository repository) {
        this.repository = repository;
    }

    public List<Manager> getAll() {
        return this.repository.getAll();
    }

    public Optional<Manager> getById(Integer id) {
        return this.repository.getById(id);
    }

    public Optional<Manager> create(Optional<Manager> data) {
        return this.repository.create(data);
    }

    public Optional<Manager> update(Integer id, Optional<Manager> data) {
        return this.repository.updateById(id, data);
    }

    public void delete(Integer id) {
        this.repository.deleteById(id);
    }
}
