package com.work.motel.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Funcionario {
    
    @JsonIgnore
    private Integer id;
    private String name;
    private String email;
    private String password;  // Senha criptografada

}