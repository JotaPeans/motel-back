package com.work.motel.domain.entities;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.work.motel.domain.enums.FormaPagamento;
import com.work.motel.domain.enums.QuartoStatus;
import com.work.motel.domain.enums.QuartoTipo;
import com.work.motel.domain.enums.ReservaStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Reserva {
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  Integer id;

  ReservaStatus status;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  Date data_checkin;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  Date data_checkout;

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
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  Double quarto_preco;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  FormaPagamento forma_pagamento;
}
