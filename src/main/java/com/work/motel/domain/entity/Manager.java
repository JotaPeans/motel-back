package com.work.motel.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Manager {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Integer id;
    Integer funcionarioId;
}
