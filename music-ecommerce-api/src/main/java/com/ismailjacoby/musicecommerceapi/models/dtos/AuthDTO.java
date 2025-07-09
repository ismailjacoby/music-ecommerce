package com.ismailjacoby.musicecommerceapi.models.dtos;

import com.ismailjacoby.musicecommerceapi.models.enums.UserRole;
import lombok.Builder;

@Builder
public record AuthDTO(
        String username,
        String token,
        UserRole role
) {
}
