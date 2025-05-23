package com.work.motel.presentation.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.point.PointSearchPaymentIntent;
import com.work.motel.application.DTOs.MercadopagoPointWebhookDTO;
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

    @PostMapping("/payment/mercadopago/pix")
    public ResponseEntity<?> ProviderWebhookPix(@RequestBody MercadopagoWebhookDTO data) {
        String action = data.getAction();

        if (action != null && action.equals("payment.updated")) {
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
                    paymentId,
                    FormaPagamento.valueOf(payment.getMetadata().get("forma_pagamento").toString()));

            Optional<Pagamento> paymentToCreate = Optional.ofNullable(pagamento);
            service.create(paymentToCreate);
        }
        return ResponseEntity.ok(null);
    }

    @PostMapping("/payment/mercadopago/pdv")
    public ResponseEntity<?> ProviderWebhookPdv(@RequestBody MercadopagoPointWebhookDTO data) {
        String state = data.getState();
        String paymentState = data.getPayment().state;

        if (state != null && state.equals("FINISHED") && paymentState != null && paymentState.equals("approved")) {
            mercadopagoIntegration.init();

            String paymentId = data.getId();
            PointSearchPaymentIntent payment = mercadopagoIntegration.getPointPaymentById(paymentId);

            String[] additionalInfo = payment.getAdditionalInfo().getExternalReference().split(":");
            String customerIdString = additionalInfo[0];
            Integer customerId = (int) Double.parseDouble(customerIdString);

            String formaPagamento = additionalInfo[1];

            Pagamento pagamento = new Pagamento(
                    null,
                    customerId,
                    null,
                    null,
                    paymentId,
                    FormaPagamento.valueOf(formaPagamento));

            Optional<Pagamento> paymentToCreate = Optional.ofNullable(pagamento);
            service.create(paymentToCreate);
        }
        return ResponseEntity.ok(null);
    }
}
