package com.work.motel.domain.entity;

import com.work.motel.enums.QuartoStatus;
import com.work.motel.enums.QuartoTipo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Quarto {
  Integer id;
  Integer numero;
  QuartoTipo tipo;
  QuartoStatus status;
  String clienteNome;
}
