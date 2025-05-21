package com.work.motel.application.serializers;

import java.math.BigDecimal;

import com.work.motel.domain.enums.FormaPagamento;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PointOrder {
    private String id;
    private BigDecimal amount;
    private FormaPagamento method;
}
