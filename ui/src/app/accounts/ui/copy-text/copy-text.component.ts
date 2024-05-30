import { animate, style, transition, trigger } from '@angular/animations'
import { NgIf } from '@angular/common'
import { Component, Input } from '@angular/core'
import { CopyDirective } from '../../util/copy.directive'

@Component({
  selector: 'app-copy-text',
  standalone: true,
  imports: [CopyDirective, NgIf],
  templateUrl: './copy-text.component.html',
  styleUrl: './copy-text.component.scss',
  animations: [
    trigger('fadeInOut', [
      transition(':enter', [
        style({ 'opacity': 0 }),
        animate('0.2s', style({ 'opacity': 1 })),
      ]),
      transition(':leave', [
        animate('0.2s', style({ 'opacity': 0 })),
      ]),
    ]),
  ],
})
export class CopyTextComponent {
  @Input()
  public payload: string = ''

  @Input()
  public lazy: boolean | null = false

  @Input()
  public possibleWidth: string = '100px'
  public copied: boolean = false

  public notify() {
    this.copied = true
    
    setTimeout(() => {
      this.copied = false
    }, 1000);
  }
}
