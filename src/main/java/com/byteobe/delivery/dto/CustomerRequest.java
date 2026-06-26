package com.byteobe.delivery.dto;

public record CustomerRequest(
        String name,
        String email,
        int phone,
        String address
) {
}
