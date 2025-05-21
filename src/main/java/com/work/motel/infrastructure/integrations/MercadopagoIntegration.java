package com.work.motel.infrastructure.integrations;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.work.motel.application.DTOs.MercadopagoDTO;
import com.work.motel.application.serializers.PixOrder;
import com.work.motel.application.serializers.PointOrder;
import com.work.motel.domain.enums.FormaPagamento;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.*;
import com.mercadopago.client.point.*;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.point.*;

@Service
public class MercadopagoIntegration {
  @Value("${mercadopago.secret}")
  private String accessToken;

  @Value("${mercadopago.device}")
  private String deviceId;

  @Value("${mercadopago.notification}")
  private String notificationUrl;

  PaymentClient paymentClient = new PaymentClient();
  PointClient pointClient = new PointClient();

  public void init() {
    MercadoPagoConfig.setAccessToken(accessToken);
  }

  public Payment getPaymentById(long id) {
    try {
      Payment payment = paymentClient.get(id);
      return payment;
    }
    catch (MPApiException | MPException e) {
      return null;
    }
  }

  public PointSearchPaymentIntent getPointPaymentById(String id) {
    try {
      PointSearchPaymentIntent payment = pointClient.searchPaymentIntent(id);
      return payment;
    } catch (MPApiException | MPException e) {
      return null;
    }
  }

  public PixOrder createPixOrder(MercadopagoDTO data) {
    Map<String,Object> metadata = new HashMap<>();
    metadata.put("customer_id", data.getCustomer_id());
    metadata.put("forma_pagamento", "PIX");

    PaymentCreateRequest request = PaymentCreateRequest.builder()
        .transactionAmount(data.getAmount())
        .paymentMethodId("pix")
        .metadata(metadata)
        .payer(
            PaymentPayerRequest.builder()
                .email(data.getCustomer_email())
                .firstName(data.getCustomer_name())
                .build())
        .notificationUrl(notificationUrl + "/webhooks/payment/mercadopago/pix")
        .installments(0)
        .build();

    try {
      Payment payment = paymentClient.create(request);
      PixOrder order = new PixOrder(
          payment.getId().toString(),
          payment.getPointOfInteraction().getTransactionData().getQrCode(),
          payment.getPointOfInteraction().getTransactionData().getQrCodeBase64());

      return order;
    } catch (MPApiException | MPException e) {
      return null;
    }
  }

  public PointOrder createCreditOrder(MercadopagoDTO data) {
    PointPaymentIntentRequest request = PointPaymentIntentRequest.builder()
        .amount(data.getAmount().multiply(new BigDecimal("100")))
        .payment(
            PointPaymentIntentPaymentRequest.builder()
                .type("credit_card")
                .installments(1)
                .installmentsCost("seller")
                .build())
        .additionalInfo(
            PointPaymentIntentAdditionalInfoRequest.builder()
                .printOnTerminal(false)
                .externalReference(data.getCustomer_id() + ":" + FormaPagamento.CREDITO)
                .build())
        .build();

    try {
      PointPaymentIntent payment = pointClient.createPaymentIntent(deviceId, request);
      PointOrder order = new PointOrder(payment.getId(), payment.getAmount(), FormaPagamento.CREDITO);

      return order;
    } catch (MPApiException | MPException e) {
      System.out.println(e.getMessage());
      return null;
    }
  }

  public PointOrder createDebitOrder(MercadopagoDTO data) {
    PointPaymentIntentRequest request = PointPaymentIntentRequest.builder()
        .amount(data.getAmount().multiply(new BigDecimal("100")))
        .payment(
            PointPaymentIntentPaymentRequest.builder()
                .type("debit_card")
                .build())
        .additionalInfo(
            PointPaymentIntentAdditionalInfoRequest.builder()
                .printOnTerminal(false)
                .externalReference(data.getCustomer_id() + ":" + FormaPagamento.DEBITO)
                .build())
        .build();

    try {
      PointPaymentIntent payment = pointClient.createPaymentIntent(deviceId, request);
      PointOrder order = new PointOrder(payment.getId(), payment.getAmount(), FormaPagamento.DEBITO);

      return order;
    } catch (MPApiException | MPException e) {
      System.out.println(e.getMessage());
      return null;
    }
  }

  public PointCancelPaymentIntent cancelPdvPayment(String paymentIntentId) {
    try {
      PointCancelPaymentIntent data = pointClient.cancelPaymentIntent(this.deviceId, paymentIntentId);
      return data;
    } catch (MPApiException | MPException e) {
      System.out.println(e.getMessage());
      return null;
    }
  }
}
