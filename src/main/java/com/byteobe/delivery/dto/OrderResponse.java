package com.byteobe.delivery.dto;



import java.time.LocalDateTime;

public record OrderResponse(
        Long  id,
        Long restaurantId,
        Long items,
        double total,
        String status,
        LocalDateTime createAt
) {
}
