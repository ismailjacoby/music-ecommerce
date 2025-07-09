import {Routes} from '@angular/router';
import {Signup} from './signup/signup';
import {Login} from './login/login';

export const authRoutes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: "full"},
  { path: 'login', component: Login },
  { path: 'signup', component: Signup },
]
