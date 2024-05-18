import { Component } from '@angular/core'

@Component({
  selector: 'app-hero-page',
  standalone: true,
  imports: [],
  templateUrl: './hero-page.component.html',
  styleUrl: './hero-page.component.scss'
})
export class HeroPageComponent {
  public getLogoSrc() {
    if (window.matchMedia('(prefers-color-scheme: dark').matches) {
      return '../assets/tiger-logo-face-dark.svg'
    } else {
      return '../assets/tiger-logo-face.svg'
    }
  }
}
