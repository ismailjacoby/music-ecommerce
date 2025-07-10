import {Routes} from '@angular/router';
import {Signup} from './signup/signup';
import {Login} from './login/login';
import {ForgotPassword} from './forgot-password/forgot-password';
import {ResetPassword} from './reset-password/reset-password';

export const authRoutes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: "full"},
  { path: 'login', component: Login },
  { path: 'signup', component: Signup },
  { path: 'forgot-password', component: ForgotPassword },
  { path: 'reset-password', component: ResetPassword },
]
