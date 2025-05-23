package com.work.motel.application.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MercadopagoWebhookDTO {
    private String action;
    private WebhookData data;
}