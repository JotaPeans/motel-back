package com.work.motel.infrastructure.integrations;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.work.motel.application.DTOs.MercadopagoDTO;
import com.work.motel.application.serializers.PixOrder;
import com.work.motel.application.serializers.PointOrder;
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

  // TODO: Quando o pagamento for efetuado com sucesso, será recebido um webhook, neste webhook ira conter o id da transação, cujo usaremos para armazenar o tabela Pagamentos. No front, vai ficar requisitando para um novo endpoint para saber se a entidade de Pagamento foi criada com o id do mercado pago x. 

  public PixOrder createPixOrder(MercadopagoDTO data) {
    PaymentCreateRequest request = PaymentCreateRequest.builder()
        .transactionAmount(data.getAmount())
        .paymentMethodId("pix")
        .payer(
            PaymentPayerRequest.builder()
                .email(data.getCustomer_email())
                .firstName(data.getCustomer_name())
                .build())
        .notificationUrl(notificationUrl)
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
                .build())
        .build();

    try {
      PointPaymentIntent payment = pointClient.createPaymentIntent(deviceId, request);
      PointOrder order = new PointOrder(payment.getId());

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
                .build())
        .build();

    try {
      PointPaymentIntent payment = pointClient.createPaymentIntent(deviceId, request);
      PointOrder order = new PointOrder(payment.getId());

      return order;
    } catch (MPApiException | MPException e) {
      System.out.println(e.getMessage());
      return null;
    }
  }
}
