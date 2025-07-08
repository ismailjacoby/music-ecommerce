import {inject, Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {SignupForm} from '../models/auth.model';

@Injectable({
  providedIn: 'root'
})
export class Auth {

  private apiUrl = environment.apiUrl + "/auth"

  http = inject(HttpClient);

  signup(signupForm: SignupForm): Observable<string>{
    return this.http.post<string>(`${this.apiUrl}/signup`, signupForm, {
      responseType: 'text' as 'json',
    });
  }
}
