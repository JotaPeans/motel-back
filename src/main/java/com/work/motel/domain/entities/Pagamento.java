package com.work.motel.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.work.motel.domain.enums.FormaPagamento;

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
  String payment_provider_id;
  FormaPagamento forma_Pagamento;
}
