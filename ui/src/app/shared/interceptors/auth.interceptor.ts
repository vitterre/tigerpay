import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http'
import { inject } from '@angular/core'
import { throwError } from 'rxjs'
import { catchError, switchMap } from 'rxjs/operators'
import { IAuthResponse } from '../../accounts/data-access/IAuthResponse'
import { AuthService } from '../../accounts/data-access/auth.service'

export const authInterceptor: HttpInterceptorFn = (request, next) => {
  const authService = inject(AuthService);
  const token: string = localStorage.getItem('accessToken') ?? '';
  const exclude: Array<string> = [
    'http://localhost:7100/api/v1/auth/register',
    'http://localhost:7100/api/v1/auth/login',
    'http://localhost:7100/api/v1/auth/refresh'
  ];

  if (!exclude.includes(request.url)) {
    request = request.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });
  }

  return next(request).pipe(
    catchError((error: HttpErrorResponse) => {
      if (error.status === 401 && !exclude.includes(request.url)) {
        return authService.refreshToken().pipe(
          switchMap((response: IAuthResponse) => {
            if (response && response.accessToken) {
              localStorage.setItem('accessToken', response.accessToken);
              localStorage.setItem('refreshToken', response.refreshToken);
              request = request.clone({
                setHeaders: {
                  Authorization: `Bearer ${response.accessToken}`
                }
              });
              return next(request);
            } else {
              localStorage.clear()
              authService.redirectToLogin();
              return throwError(() => error);
            }
          }),
          catchError(() => {
            localStorage.clear()
            authService.redirectToLogin();
            return throwError(() => error);
          })
        );
      }
      return throwError(() => error);
    })
  );
};