import { animate, style, transition, trigger } from '@angular/animations'
import { CommonModule } from '@angular/common'
import { Component, Input } from '@angular/core'
import { CardComponent } from '../../../shared/card/card.component'
import { IDropdownElement } from '../../../shared/dropdown/IDropdownElement'
import { InputButtonComponent } from '../../../shared/input-button/input-button.component'
import { ModalComponent } from '../../../shared/modal/modal.component'
import { ModalService } from '../../../shared/modal/modal.service'
import { DashboardTransferFormComponent } from '../dashboard-transfer/dashboard-transfer-form.component'

@Component({
  selector: 'app-dashboard-payments',
  standalone: true,
  imports: [
    CommonModule,
    CardComponent,
    InputButtonComponent,
    ModalComponent,
    DashboardTransferFormComponent
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
  public accountItems: IDropdownElement[] = []
  
  @Input()
  public modalService: ModalService
}