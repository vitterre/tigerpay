import { Routes } from '@angular/router'

export const routes: Routes = [
  {
    path: '',
    loadChildren: () =>
      import('./hero/feature/hero.routes').then(m => m.routes)
  },
  {
    path: 'accounts',
    loadChildren: () =>
      import('./accounts/feature/accounts-shell/accounts.routes').then(m => m.routes)
  },
  {
    path: 'dashboard',
    loadChildren: () =>
      import('./dashboard/feature/dashboard-shell/dashboard.routes').then(m => m.routes)
  }
]
