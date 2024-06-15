import { animate, style, transition, trigger } from '@angular/animations'
import { CommonModule } from '@angular/common'
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core'
import { IInputDropdownElement } from './IInputDropdownElement'

@Component({
  selector: 'app-input-dropdown',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './input-dropdown.component.html',
  styleUrl: './input-dropdown.component.scss',
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
export class InputDropdownComponent implements OnInit {

  public isOpen: boolean = false

  @Input()
  public selectLabel: string = 'Select item'

  @Input()
  public showLabel: boolean = true

  @Input()
  public selectedIndex: number = 0
  
  @Input()
  public items: Array<IInputDropdownElement> = [
  ]

  @Output()
  public onSelect: EventEmitter<number> = new EventEmitter()

  public selectedItem: IInputDropdownElement

  public ngOnInit(): void {
    this.selectedItem = this.items[this.selectedIndex]
  }
  

  public selectItem($index: number) {
    this.selectedItem = this.items[$index]
    this.isOpen = false
    this.onSelect.emit($index)
  }
}
