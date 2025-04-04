package com.work.motel.domain.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.work.motel.enums.QuartoStatus;
import com.work.motel.enums.QuartoTipo;
import com.work.motel.enums.ReservaStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Reserva {
  @JsonIgnore
  Integer Id;

  ReservaStatus status;

  @JsonIgnore
  Date data;

  Integer funcionarioId;
  Integer clienteId;
  Integer quartoId;

  @JsonIgnore
  String cliente_nome;

  @JsonIgnore
  String quarto_numero;
  @JsonIgnore
  QuartoTipo quarto_tipo;
  @JsonIgnore
  QuartoStatus quarto_status;
}
