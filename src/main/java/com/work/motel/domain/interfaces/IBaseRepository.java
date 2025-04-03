package com.work.motel.domain.interfaces;

import java.util.List;
import java.util.Optional;

import com.work.motel.domain.entity.Quarto;

public interface IBaseRepository {
  List<Quarto> getAll();
  Optional<Quarto> getById(Integer id);
  Optional<Quarto> create(Optional<Quarto> data);
  Optional<Quarto> updateById(Integer id, Quarto data);
  void deleteById(Integer id);
}
