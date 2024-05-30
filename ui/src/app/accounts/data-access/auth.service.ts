import { HttpClient, HttpErrorResponse } from '@angular/common/http'
import { Injectable } from '@angular/core'
import { Router } from '@angular/router'
import { Observable, catchError, throwError } from 'rxjs'
import { IAuthResponse } from './IAuthResponse'
import { ILogIn } from './ILogIn'
import { ISignUp } from './ISignUp'

@Injectable({
  providedIn: 'any'
})
export class AuthService {

  constructor(
    private httpClient: HttpClient,
    private router: Router
  ) { }

  public login(loginRequest: ILogIn) {
    console.log(`Log In: ${JSON.stringify(loginRequest)}`)

    return this.httpClient
      .post<IAuthResponse>('http://localhost:8000/api/v1/auth/login', loginRequest)
      .pipe(
        catchError((error: HttpErrorResponse) => {
          if (error.status === 400 || error.status === 401) {
            return throwError(() => new Error(error.error.message))
          } else {
            return throwError(() => new Error("Unexpected"))
          }
        })
      )
  }

  public signup(signupRequest: ISignUp): Observable<IAuthResponse> {
    console.log(`Sign Up: ${JSON.stringify(signupRequest)}`)

    return this.httpClient
      .post<IAuthResponse>('http://localhost:8000/api/v1/auth/register', signupRequest)
      .pipe(
        catchError((error: HttpErrorResponse) => {
          if (error.status === 400 || error.status === 409) {
            return throwError(() => new Error(error.error.message))
          } else {
            return throwError(() => new Error("Unexpected"))
          }
        })
      )
  }

  public refreshToken(): Observable<any> {
    return this.httpClient
      .post<IAuthResponse>('http://localhost:8000/api/v1/auth/refresh', {
        refreshToken: localStorage.getItem('refreshToken') ?? ''
      })
  }

  public redirectToLogin(): void {
    this.router.navigateByUrl('/accounts/login')
  }
}
