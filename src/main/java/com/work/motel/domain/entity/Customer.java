package com.work.motel.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Customer {
  Integer id;
  String nome;
  String telefone;
  String email;
  String CPF;
  String RG;
}
