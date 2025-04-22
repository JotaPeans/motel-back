package com.work.motel.domain.entities;

import com.work.motel.domain.enums.QuartoStatus;
import com.work.motel.domain.enums.QuartoTipo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Quarto {
  Integer id;
  Integer numero;
  QuartoTipo tipo;
  QuartoStatus status;
  Float valor;
  String clienteNome;
  Integer reservaId;
}
