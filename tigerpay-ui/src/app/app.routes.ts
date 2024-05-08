import { Routes } from '@angular/router'
import { HeroPageComponent } from './pages/hero-page/hero-page.component'

export const routes: Routes = [
  {
    path: '',
    component: HeroPageComponent
  },
  {
    path: 'login',
    loadChildren: () =>
      import('./pages/login-page/login-page.routes').then(m => m.routes)
  },
  {
    path: 'signup',
    loadChildren: () =>
      import('./pages/signup-page/signup-page.routes').then(m => m.routes)
  },
  {
    path: 'dashboard',
    loadChildren: () =>
      import('./pages/dashboard-page/dashboard-page.routes').then(m => m.routes)
  }
]
