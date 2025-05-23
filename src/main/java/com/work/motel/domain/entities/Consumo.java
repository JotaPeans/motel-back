package com.work.motel.domain.entities;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Consumo {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;

    private Integer produtoId;
    private Integer servicoId;
    private Integer clienteId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date data_consumo;

    private Integer quantidade;
    private Double valor;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String cliente_nome;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String produto_nome;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String servico_nome;
}
