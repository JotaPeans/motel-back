package com.work.motel.application.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class MercadopagoDTO {
    @NotNull(message = "O valor do pagamento é obrigatório")
    private BigDecimal amount;

    @NotNull(message = "O Id do cliente é obrigatório")
    private Integer customer_id;

    @NotNull(message = "O Email do cliente é obrigatório")
    private String customer_email;

    @NotNull(message = "O Nome do cliente é obrigatório")
    private String customer_name;
}
