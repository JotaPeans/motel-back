package com.work.motel.infrastructure.Serializers;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CadastroRequest {

    private String name;
    private String email;
    private String password;
}
