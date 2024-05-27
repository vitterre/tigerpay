import { Component, Input } from '@angular/core'

@Component({
  selector: 'app-input-button',
  standalone: true,
  imports: [],
  templateUrl: './input-button.component.html',
  styleUrl: './input-button.component.scss'
})
export class InputButtonComponent {
  
  @Input()
  public type: string

  @Input()
  public name: string
}
