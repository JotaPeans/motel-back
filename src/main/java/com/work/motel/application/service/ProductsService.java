package com.work.motel.application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.work.motel.domain.entities.Product;
import com.work.motel.infrastructure.repositories.ProductsRepository;

@Service
public class ProductsService {

    private final ProductsRepository repository;

    @Autowired
    public ProductsService(ProductsRepository repository) {
        this.repository = repository;
    }

    public List<Product> getAll() {
        return repository.getAll();
    }

    public Optional<Product> getById(Integer id) {
        return repository.getById(id);
    }

    public Optional<Product> create(Optional<Product> data) {
        return repository.create(data);
    }

    public Optional<Product> update(Integer id, Optional<Product> data) {
        return repository.updateById(id, data);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
