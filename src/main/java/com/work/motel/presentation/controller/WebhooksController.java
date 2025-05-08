package com.work.motel.presentation.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mercadopago.resources.payment.Payment;
import com.work.motel.application.DTOs.MercadopagoWebhookDTO;
import com.work.motel.application.service.PagamentoService;
import com.work.motel.domain.entities.Pagamento;
import com.work.motel.domain.enums.FormaPagamento;
import com.work.motel.infrastructure.integrations.MercadopagoIntegration;

@RequestMapping("/webhooks")
@RestController
public class WebhooksController {

    @Autowired
    private PagamentoService service; // Injeção de dependência diretamente no campo

    @Autowired
    private MercadopagoIntegration mercadopagoIntegration;

    @PostMapping("/payment/mercadopago")
    public ResponseEntity<?> ProviderWebhook(@RequestBody MercadopagoWebhookDTO data) {
        String action = data.getAction();

        if(action != null && action.equals("payment.updated")) {
            mercadopagoIntegration.init();

            String paymentId = data.getData().getId();
            Payment payment = mercadopagoIntegration.getPaymentById(Long.parseLong(paymentId));

            String customerIdString = payment.getMetadata().get("customer_id").toString();
            Integer customerId = (int) Double.parseDouble(customerIdString);

            Pagamento pagamento = new Pagamento(
                null,
                customerId,
                null,
                null,
                Long.parseLong(paymentId),
                FormaPagamento.valueOf(payment.getMetadata().get("forma_pagamento").toString())
            );

            Optional<Pagamento> paymentToCreate = Optional.ofNullable(pagamento);
            service.create(paymentToCreate);
        }
        return ResponseEntity.ok(null);
    }
}
