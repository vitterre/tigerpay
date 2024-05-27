import { animate, style, transition, trigger } from '@angular/animations'
import { CommonModule } from '@angular/common'
import { Component, Input } from '@angular/core'
import { CardComponent } from '../../../shared/card/card.component'
import { InputButtonComponent } from '../../../shared/input-button/input-button.component'
import { ModalService } from '../../../shared/modal/modal.service'

@Component({
  selector: 'app-dashboard-payments',
  standalone: true,
  imports: [
    CommonModule,
    CardComponent,
    InputButtonComponent
  ],
  templateUrl: './dashboard-payments.component.html',
  animations: [
    trigger('fadeIn', [
      transition(':enter', [
        style({ 'opacity': 0 }),
        animate('0.3s', style({ 'opacity': 1 })),
      ])
    ]),
  ]
})
export class DashboardPaymentsComponent {
  
  @Input()
  public modalService: ModalService
}