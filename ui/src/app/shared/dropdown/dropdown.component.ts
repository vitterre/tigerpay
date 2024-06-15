import { animate, style, transition, trigger } from '@angular/animations'
import { CommonModule } from '@angular/common'
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core'
import { IDropdownElement } from './IDropdownElement'

@Component({
  selector: 'app-dropdown',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dropdown.component.html',
  styleUrl: './dropdown.component.scss',
  animations: [
    trigger('fadeInOut', [
      transition(':enter', [
        style({ 'opacity': 0 }),
        animate('0.1s', style({ 'opacity': 1 })),
      ]),
      transition(':leave', [
        style({ 'opacity': 1 }),
        animate('0.1s', style({ 'opacity': 0 })),
      ])
    ]),
  ]
})
export class DropdownComponent implements OnInit {

  public isOpen: boolean = false

  @Input()
  public selectLabel: string = 'Select item'

  @Input()
  public showLabel: boolean = true

  @Input()
  public selectedIndex: number = 0
  
  @Input()
  public items: Array<IDropdownElement> = [
    {
      label: 'first',
      secondary: '111111',
    },
    {
      label: 'second',
      secondary: '222222'
    },
    {
      label: 'third',
      secondary: '333333'
    }
  ]

  @Output()
  public onSelect: EventEmitter<number> = new EventEmitter()

  public selectedItem: IDropdownElement

  public ngOnInit(): void {
    this.selectedItem = this.items[this.selectedIndex]
  }
  

  public selectItem($index: number) {
    this.selectedItem = this.items[$index]
    this.isOpen = false
    this.onSelect.emit($index)
  }
}
