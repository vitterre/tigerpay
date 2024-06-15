import { NgIf } from '@angular/common'
import { Component, Input } from '@angular/core'

@Component({
  selector: 'app-card-button',
  standalone: true,
  imports: [NgIf],
  templateUrl: './card-button.component.html',
  styleUrl: './card-button.component.scss'
})
export class CardButtonComponent {
  
  @Input()
  public label: string = 'Button'

  @Input()
  public iconUrl: string = ''
}
