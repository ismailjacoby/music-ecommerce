package com.ismailjacoby.musicecommerceapi.models.forms;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SignupForm(
        @NotBlank(message = "First name is required")
        @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
        String firstName,

        @NotBlank(message = "Last name is required")
        @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
        String lastName,

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
        String password,

        @Pattern(
                regexp = "^$|^\\+?[0-9]{6,15}$",
                message = "Phone number must be valid or left empty"
        )
        String phoneNumber
) {
}