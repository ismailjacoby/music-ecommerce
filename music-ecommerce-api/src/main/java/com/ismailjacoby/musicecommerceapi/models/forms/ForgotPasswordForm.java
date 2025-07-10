package com.ismailjacoby.musicecommerceapi.models.forms;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ForgotPasswordForm(
        @NotBlank(message = "Email is required")
        @Pattern(
                regexp = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$",
                message = "Invalid email format"
        )
        String email
) {
}
