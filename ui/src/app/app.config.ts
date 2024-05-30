import { ApplicationConfig } from '@angular/core'
import { provideRouter, withViewTransitions } from '@angular/router'

import { provideHttpClient, withInterceptors } from '@angular/common/http'
import { provideAnimations } from '@angular/platform-browser/animations'
import { routes } from './app.routes'
import { authInterceptor } from './shared/interceptors/auth.interceptor'

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(
      routes,
      withViewTransitions()
    ),
    provideAnimations(),
    provideHttpClient(
      withInterceptors([authInterceptor])
    )
  ]
};
