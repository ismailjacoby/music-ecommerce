import {Component, inject, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {Auth} from '../../../services/auth';
import {Router, RouterLink} from '@angular/router';

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    RouterLink
  ],
  templateUrl: './signup.html',
  styleUrl: './signup.css'
})
export class Signup implements OnInit{
  errorMessage: string = "";
  successMessage: string = "";

  authService = inject(Auth);
  formBuilder = inject(FormBuilder);
  router = inject(Router);

  signupForm: FormGroup = new FormGroup({});

  ngOnInit(): void {
    this.signupForm = this.formBuilder.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(8)]],
      phoneNumber: ['']
    })
  }

  signup(){
    if(this.signupForm.invalid){
      return;
    }

    this.authService.signup(this.signupForm.value).subscribe({
      next: (response) => {
        this.signupForm.reset();
        this.successMessage = response;
        this.errorMessage = '';
        setTimeout(() => this.router.navigate(['/auth/login']), 3000);
      }, error: (error) => {
        console.error(error);
        this.errorMessage = error.error.message || 'An unexpected error occurred. Please try again.';
      }
    })
  }

}
