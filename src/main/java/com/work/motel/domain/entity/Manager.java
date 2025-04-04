package com.work.motel.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Manager {
    @JsonIgnore

    Integer id;
    Integer funcionarioId;
    String nome;
}
