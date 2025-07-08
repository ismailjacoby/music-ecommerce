package com.ismailjacoby.musicecommerceapi.controllers;

import com.ismailjacoby.musicecommerceapi.models.forms.SignupForm;
import com.ismailjacoby.musicecommerceapi.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody @Valid  SignupForm form) {
        userService.signup(form);
        return ResponseEntity.ok("Signup successful");
    }
}
