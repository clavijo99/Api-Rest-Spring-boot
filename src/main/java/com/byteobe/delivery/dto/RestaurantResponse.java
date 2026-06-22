package com.byteobe.delivery.dto;

public record RestaurantResponse(
        Long id,
        String name,
        String address,
        String phone,
        String category,
        boolean active
) {
}
