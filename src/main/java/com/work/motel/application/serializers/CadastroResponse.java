package com.work.motel.application.serializers;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CadastroResponse {
    private String name;
    private String email;
}
