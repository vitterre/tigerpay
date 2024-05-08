import { NgClass, NgFor } from '@angular/common'
import { Component, EventEmitter, Input, Output } from '@angular/core'

@Component({
  selector: 'app-tab-bar',
  standalone: true,
  imports: [NgFor, NgClass],
  templateUrl: './tab-bar.component.html',
  styleUrl: './tab-bar.component.scss'
})
export class TabBarComponent {
  @Input() items: Array<any>
  @Input() labelField: string
  @Input() active: any
  @Output() onTabSelect: EventEmitter<any> = new EventEmitter()


  public select(tab: any) {
    this.onTabSelect.emit(tab)
  }

}
