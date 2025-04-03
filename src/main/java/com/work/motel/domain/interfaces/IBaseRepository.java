package com.work.motel.domain.interfaces;

import java.util.List;

import com.work.motel.domain.entity.Quarto;

public interface IBaseRepository {
  List<Quarto> getAll();
  Quarto getById();
  Quarto create();
  Quarto update();
  void delete();
}
