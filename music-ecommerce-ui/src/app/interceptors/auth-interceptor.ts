import { HttpInterceptorFn } from '@angular/common/http';
import {Auth} from '../services/auth';
import {inject} from '@angular/core';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(Auth);

  if (authService.isLoggedIn()) {
    const token = localStorage.getItem('token');

    if (token) {
      const cloned = req.clone({
        setHeaders: {
          Authorization: `${token}`,
        },
      });
      return next(cloned);
    }
  }

  return next(req);
};
