package com.ismailjacoby.musicecommerceapi.models.forms;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record LoginForm(
        @NotBlank(message = "Email is required")
        @Pattern(
                regexp = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$",
                message = "Invalid email format"
        )
        String email,

        @NotBlank(message = "Password is required")
        @Size(min = 8, max = 100, message = "Password must be at least 8 characters")

        @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[_#?!@=$ %^&*-]).{8,}$",
                message = "Password must be at least 8 characters long, include one uppercase letter, one lowercase letter, one number and one special character")
        String password
) {
}
