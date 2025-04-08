package com.work.motel.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Manager {
    @JsonIgnore

    Integer id;
    Integer funcionarioId;
}
