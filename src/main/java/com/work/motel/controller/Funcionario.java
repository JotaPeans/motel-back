package com.work.motel.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/employee")
@RestController
public class Funcionario {

  @GetMapping
  public static String getFuncionario() {
    return "funcionario tal";
  }
}
