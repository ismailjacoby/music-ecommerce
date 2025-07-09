package com.ismailjacoby.musicecommerceapi.controllers;

import com.ismailjacoby.musicecommerceapi.models.dtos.AuthDTO;
import com.ismailjacoby.musicecommerceapi.models.forms.LoginForm;
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
}
