package com.work.motel.infrastructure.Serializers;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CadastroResponse {
    private String name;
    private String email;
}
