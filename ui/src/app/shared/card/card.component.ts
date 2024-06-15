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
  
  @Input() title: string = 'Card title'
  @Input() displayTitle: boolean = true
  @Input() gradientBg: boolean = false
  // right, bottom-right, top-right
  @Input() gridPosition: string = 'center'
}
