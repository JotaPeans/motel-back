package com.work.motel.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {
    private Integer id;
    private String nome;
    private String tipo;
    private Double valor;
    private Double custo;
}
