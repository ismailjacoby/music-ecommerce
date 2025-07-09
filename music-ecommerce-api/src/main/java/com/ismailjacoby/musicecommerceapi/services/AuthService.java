package com.ismailjacoby.musicecommerceapi.services;

import com.ismailjacoby.musicecommerceapi.models.dtos.AuthDTO;
import com.ismailjacoby.musicecommerceapi.models.forms.LoginForm;
import com.ismailjacoby.musicecommerceapi.models.forms.SignupForm;

public interface AuthService {
    AuthDTO login(LoginForm form);
    void signup(SignupForm form);
}
