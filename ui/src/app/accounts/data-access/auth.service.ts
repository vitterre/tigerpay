import { Injectable } from '@angular/core'
import { ILogIn } from './ILogIn'
import { ISignUp } from './ISignUp'

@Injectable({
  providedIn: 'any'
})
export class AuthService {

  constructor() { }

  public login(loginRequest: ILogIn) {
    console.log(`Log In: ${loginRequest}`)
  }

  public signup(signupRequest: ISignUp) {
    console.log(`Sign Up: ${signupRequest}`)
  }
}
