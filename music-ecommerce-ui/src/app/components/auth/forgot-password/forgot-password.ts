import {Component, inject, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {Router, RouterLink} from '@angular/router';
import {Auth} from '../../../services/auth';

@Component({
  selector: 'app-forgot-password',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    RouterLink
  ],
  templateUrl: './forgot-password.html',
  styleUrl: './forgot-password.css'
})
export class ForgotPassword implements OnInit{
  errorMessage: string = "";
  successMessage: string = "";

  forgotPasswordForm: FormGroup = new FormGroup({});

  authService = inject(Auth);
  formBuilder = inject(FormBuilder);
  router = inject(Router);

  ngOnInit(): void {
    this.forgotPasswordForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
    })
  }

  forgotPassword() {
    if (this.forgotPasswordForm.invalid) return;

    this.authService.forgotPassword(this.forgotPasswordForm.value).subscribe({
      next: (response) => {
        this.successMessage = response;
        this.errorMessage = '';
        this.forgotPasswordForm.reset();
      },
      error: (error) => {
        this.errorMessage = error.error.message || 'Something went wrong.';
        this.successMessage = '';
      }
    });
  }
}
