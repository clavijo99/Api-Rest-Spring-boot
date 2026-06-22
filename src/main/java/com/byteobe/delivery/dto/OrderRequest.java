package com.byteobe.delivery.dto;


public record OrderRequest(
        Long restaurantId,
        Long items,
        double total,
        String status
) {
}
