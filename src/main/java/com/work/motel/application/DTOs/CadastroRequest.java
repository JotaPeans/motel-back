package com.work.motel.application.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CadastroRequest {

    private String name;
    private String email;
    private String password;
}
