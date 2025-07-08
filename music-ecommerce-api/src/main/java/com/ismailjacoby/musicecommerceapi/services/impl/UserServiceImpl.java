package com.ismailjacoby.musicecommerceapi.services.impl;

import com.ismailjacoby.musicecommerceapi.exceptions.DuplicateException;
import com.ismailjacoby.musicecommerceapi.models.entities.User;
import com.ismailjacoby.musicecommerceapi.models.enums.UserRole;
import com.ismailjacoby.musicecommerceapi.models.forms.SignupForm;
import com.ismailjacoby.musicecommerceapi.repositories.UserRepository;
import com.ismailjacoby.musicecommerceapi.services.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
}
