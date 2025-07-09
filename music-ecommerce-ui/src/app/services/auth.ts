import {inject, Injectable, signal} from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Observable, tap} from 'rxjs';
import {AuthDTO, LoginForm, SignupForm} from '../models/auth.model';
import {Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class Auth {
  private apiUrl = environment.apiUrl + "/auth"

  http = inject(HttpClient);
  router = inject(Router);

  isLoggedIn = signal<boolean>(!!localStorage.getItem('token'));

  login(loginForm: LoginForm): Observable<AuthDTO>{
    return this.http.post<AuthDTO>(`${this.apiUrl}/login`, loginForm).pipe(
      tap((authDTO) => {
        localStorage.setItem('token', authDTO.token);
        localStorage.setItem('username', authDTO.username);
        localStorage.setItem('role', authDTO.role.toString());

        this.isLoggedIn.set(true);
      })
    )
  }

  logout(): void{
    localStorage.removeItem('token');
    localStorage.removeItem('username');
    localStorage.removeItem('role');

    this.isLoggedIn.set(false);
    this.router.navigate(['/']);
  }

  signup(signupForm: SignupForm): Observable<string>{
    return this.http.post<string>(`${this.apiUrl}/signup`, signupForm, {
      responseType: 'text' as 'json',
    });
  }
}
