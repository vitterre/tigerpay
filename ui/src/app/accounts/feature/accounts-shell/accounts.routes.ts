import { Route } from '@angular/router'
import { AccountsLoginComponent } from '../accounts-login/accounts-login.component'
import { AccountsSignupComponent } from '../accounts-signup/accounts-signup.component'

export const routes: Route[] = [
  {
    path: 'login',
    component: AccountsLoginComponent
  },
  {
    path: 'signup',
    component: AccountsSignupComponent
  },
]