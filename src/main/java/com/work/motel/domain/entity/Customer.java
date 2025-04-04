package com.work.motel.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Customer {
  @JsonIgnore
  Integer id;
  
  String nome;
  String telefone;
  String email;
  String CPF;
  String RG;
}
