package com.ismailjacoby.musicecommerceapi.services;

import com.ismailjacoby.musicecommerceapi.models.dtos.AuthDTO;
import com.ismailjacoby.musicecommerceapi.models.forms.ForgotPasswordForm;
import com.ismailjacoby.musicecommerceapi.models.forms.LoginForm;
import com.ismailjacoby.musicecommerceapi.models.forms.ResetPasswordForm;
import com.ismailjacoby.musicecommerceapi.models.forms.SignupForm;

public interface AuthService {
    AuthDTO login(LoginForm form);
    void signup(SignupForm form);
    void forgotPassword(ForgotPasswordForm form);
    void resetPassword(ResetPasswordForm form);
}
