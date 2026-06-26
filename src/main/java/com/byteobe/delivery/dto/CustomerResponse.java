package com.byteobe.delivery.dto;

public record CustomerResponse(
        Long id,
        String name,
        String email,
        int phone,
        String address
) {
}
