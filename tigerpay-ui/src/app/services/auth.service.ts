import { Injectable } from '@angular/core'
import { ILogIn } from '../models/ILogIn'
import { ISignUp } from '../models/ISignUp'

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
