import {Component, inject, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {Router, RouterLink} from '@angular/router';
import {Auth} from '../../../services/auth';


@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class Login implements OnInit{
  errorMessage: string = "";
  successMessage: string = "";

  loginForm: FormGroup = new FormGroup({});

  authService = inject(Auth);
  formBuilder = inject(FormBuilder);
  router = inject(Router);

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(8)]]
    })
  }



  login(){
    if(this.loginForm.invalid){
      return;
    }

    this.authService.login(this.loginForm.value).subscribe({
      next: (response) => {
      this.loginForm.reset();
      this.successMessage = 'Login successful!'
      this.errorMessage = ''
      setTimeout(()=> {
        this.router.navigate(['/'])
      }, 1500)
    },
      error: (error) => {
      console.error(error);
      this.errorMessage = error.error.message || 'An unexpected error occurred. Please try again.';
      this.successMessage = '';
      }
    })
  }

}
