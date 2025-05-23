package com.work.motel.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Customer {
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  Integer id;
  
  String nome;
  String telefone;
  String email;
  String CPF;
  String RG;
}
