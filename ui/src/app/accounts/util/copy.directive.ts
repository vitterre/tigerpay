import { Clipboard } from '@angular/cdk/clipboard'
import { Directive, EventEmitter, HostListener, Input, Output } from '@angular/core'

@Directive({
  selector: '[appCopy]',
  standalone: true
})
export class CopyDirective {

  @Input() payload: string = ''

  @Output() copied: EventEmitter<string> = new EventEmitter<string>();

  constructor(private clipboard: Clipboard) { }

  @HostListener('click')
  public onClick(): void {
    if (this.payload) {
      this.clipboard.copy(this.payload)
      this.copied.emit(this.payload)
    }
  }
}
