package com.work.motel.application.serializers;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PixOrder {
    private String id;
    private String code;
    private String image_b64;
}
