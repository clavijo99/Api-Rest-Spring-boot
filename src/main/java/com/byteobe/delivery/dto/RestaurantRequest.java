package com.byteobe.delivery.dto;

public record RestaurantRequest(
        String name,
        String address,
        String phone,
        String category
) {
}
