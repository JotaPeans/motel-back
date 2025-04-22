package com.work.motel.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.work.motel.enums.FormaPagamento;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pagamento {
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  Integer id;
  Integer clienteId;
  Integer reservaId;
  Integer consumoId;
  FormaPagamento forma_Pagamento;
}
