package com.ismailjacoby.musicecommerceapi.controllers;

import com.ismailjacoby.musicecommerceapi.models.dtos.AuthDTO;
import com.ismailjacoby.musicecommerceapi.models.forms.ForgotPasswordForm;
import com.ismailjacoby.musicecommerceapi.models.forms.LoginForm;
import com.ismailjacoby.musicecommerceapi.models.forms.ResetPasswordForm;
import com.ismailjacoby.musicecommerceapi.models.forms.SignupForm;
import com.ismailjacoby.musicecommerceapi.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("login")
    public ResponseEntity<AuthDTO> login(@RequestBody @Valid LoginForm form) {
        AuthDTO authDTO = authService.login(form);
        return ResponseEntity.ok(authDTO);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody @Valid  SignupForm form) {
        authService.signup(form);
        return ResponseEntity.ok("Signup successful");
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody @Valid ForgotPasswordForm form) {
        authService.forgotPassword(form);
        return ResponseEntity.ok("If the email exists, a reset link has been sent.");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody @Valid ResetPasswordForm form) {
        authService.resetPassword(form);
        return ResponseEntity.ok("Your password has been reset successfully.");
    }

}
