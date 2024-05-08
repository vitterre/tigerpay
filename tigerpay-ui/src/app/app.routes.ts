import { Routes } from '@angular/router'
import { HeroPageComponent } from './pages/hero-page/hero-page.component'
import { LoginPageComponent } from './pages/login-page/login-page.component'
import { SignupPageComponent } from './pages/signup-page/signup-page.component'

export const routes: Routes = [
  {
    path: '',
    component: HeroPageComponent
  },
  {
    path: 'login',
    component: LoginPageComponent
  },
  {
    path: 'signup',
    component: SignupPageComponent
  }
]
