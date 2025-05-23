package com.work.motel.application.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MercadopagoPointWebhookDTO {
    public PointWebhookAdditionalInfo additional_info;
    public int amount;
    public int caller_id;
    public long client_id;
    public String created_at;
    public String id;
    public String intent_type;
    public PointWebhookPayment payment;
    public String state;
}