import {Component, inject, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {Auth} from '../../../services/auth';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-reset-password',
  imports: [
    ReactiveFormsModule
  ],
  templateUrl: './reset-password.html',
  styleUrl: './reset-password.css'
})
export class ResetPassword implements OnInit{
  errorMessage = '';
  successMessage = '';
  token: string = '';

  resetPasswordForm: FormGroup = new FormGroup({});

  activatedRoute = inject(ActivatedRoute);
  authService = inject(Auth);
  formbuilder = inject(FormBuilder);
  router = inject(Router);

  ngOnInit() {
    this.token = this.activatedRoute.snapshot.queryParamMap.get('token') || '';
    this.resetPasswordForm = this.formbuilder.group({
      password: ['', [Validators.required, Validators.minLength(8)]]
    });
  }

  resetPassword() {
    if (this.resetPasswordForm.invalid) return;

    const payload = {
      token: this.token,
      password: this.resetPasswordForm.value.password
    };

    this.authService.resetPassword(payload).subscribe({
      next: (response) => {
        this.successMessage = response;
        this.errorMessage = '';
        this.resetPasswordForm.reset();
        setTimeout(() => this.router.navigate(['/auth/login']), 2000);
      },
      error: (error) => {
        this.errorMessage = error.error.message || 'Something went wrong.';
        this.successMessage = '';
      }
    });
  }
}
