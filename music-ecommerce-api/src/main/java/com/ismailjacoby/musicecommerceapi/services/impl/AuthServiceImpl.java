package com.ismailjacoby.musicecommerceapi.services.impl;

import com.ismailjacoby.musicecommerceapi.exceptions.DuplicateException;
import com.ismailjacoby.musicecommerceapi.exceptions.NotFoundException;
import com.ismailjacoby.musicecommerceapi.models.dtos.AuthDTO;
import com.ismailjacoby.musicecommerceapi.models.entities.User;
import com.ismailjacoby.musicecommerceapi.models.enums.UserRole;
import com.ismailjacoby.musicecommerceapi.models.forms.ForgotPasswordForm;
import com.ismailjacoby.musicecommerceapi.models.forms.LoginForm;
import com.ismailjacoby.musicecommerceapi.models.forms.ResetPasswordForm;
import com.ismailjacoby.musicecommerceapi.models.forms.SignupForm;
import com.ismailjacoby.musicecommerceapi.repositories.UserRepository;
import com.ismailjacoby.musicecommerceapi.security.JwtProvider;
import com.ismailjacoby.musicecommerceapi.services.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public AuthDTO login(LoginForm form) {
        if(form == null) {
            throw new IllegalArgumentException("Login form cannot be null.");
        }

        String username = form.email().toLowerCase().trim();

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, form.password())
            );
        } catch (BadCredentialsException e){
            throw new BadCredentialsException("Invalid username or password.");
        } catch (AuthenticationException e){
            throw new RuntimeException("Authentication failed." + e.getMessage());
        }

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new NotFoundException("User not found."));

        String token = jwtProvider.generateToken(username, user.getRole());

        return AuthDTO.builder()
                .token(token)
                .username(username)
                .role(user.getRole())
                .build();
    }

    @Override
    public void signup(SignupForm form) {
        if (form == null) {
            throw new IllegalArgumentException("Form cannot be null");
        }

        String userEmail = form.email().trim().toLowerCase();

        if(userRepository.existsByEmail(userEmail)){
            throw new DuplicateException("Email already in use");
        }

        User user = new User();
        user.setFirstName(form.firstName().trim());
        user.setLastName(form.lastName().trim());
        user.setEmail(userEmail);
        user.setPassword(passwordEncoder.encode(form.password().trim()));
        if (form.phoneNumber() != null && !form.phoneNumber().isBlank()) {
            user.setPhoneNumber(form.phoneNumber().trim());
        }
        user.setEmailVerified(false);
        user.setRole(UserRole.CUSTOMER);
        user.setActive(true);

        userRepository.save(user);
    }

    @Override
    public void forgotPassword(ForgotPasswordForm form) {
        String email = form.email().trim().toLowerCase();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("No account found with that email."));

        String token = jwtProvider.generateResetToken(user.getEmail());

        // TODO: Send email
        System.out.println("RESET LINK: https://your-frontend.com/reset-password?token=" + token);
    }

    @Override
    public void resetPassword(ResetPasswordForm form) {
        String email = jwtProvider.validateResetToken(form.token());

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found."));

        user.setPassword(passwordEncoder.encode(form.password().trim()));
        userRepository.save(user);
    }
}
