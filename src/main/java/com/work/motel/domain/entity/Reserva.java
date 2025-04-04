package com.work.motel.domain.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.work.motel.enums.QuartoStatus;
import com.work.motel.enums.QuartoTipo;
import com.work.motel.enums.ReservaStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Reserva {
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  Integer Id;

  ReservaStatus status;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  Date data;

  Integer funcionarioId;
  Integer clienteId;
  Integer quartoId;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  String cliente_nome;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  String quarto_numero;
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  QuartoTipo quarto_tipo;
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  QuartoStatus quarto_status;
}
