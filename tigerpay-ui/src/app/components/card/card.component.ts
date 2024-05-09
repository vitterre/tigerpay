import { NgClass, NgIf } from '@angular/common'
import { Component, Input } from '@angular/core'

@Component({
  selector: 'app-card',
  standalone: true,
  imports: [NgIf, NgClass],
  templateUrl: './card.component.html',
  styleUrl: './card.component.scss'
})
export class CardComponent {
  
  @Input() gradientBg: boolean = false
  
  // left, right, top, bottom, left-top, left-bottom, right-top, right-bottom, center

  // right, bottom-right, top-right
  @Input() gridPosition: string = 'center'
}
