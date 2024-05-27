import { CommonModule } from '@angular/common'
import { Component, Input } from '@angular/core'

@Component({
  selector: 'app-tag',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './tag.component.html',
  styleUrl: './tag.component.scss'
})
export class TagComponent {
  
  @Input()
  public tag: string = 'Tag'

  @Input()
  public color: string = 'white'

  @Input()
  public extra: string = ''
}
