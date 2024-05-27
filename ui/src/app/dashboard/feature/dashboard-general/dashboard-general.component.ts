import { animate, style, transition, trigger } from '@angular/animations'
import { CommonModule } from '@angular/common'
import { Component, EventEmitter, Input, Output } from '@angular/core'
import { CopyTextComponent } from '../../../accounts/ui/copy-text/copy-text.component'
import { CardButtonComponent } from '../../../shared/card-button/card-button.component'
import { CardComponent } from '../../../shared/card/card.component'
import { ModalService } from '../../../shared/modal/modal.service'

@Component({
  selector: 'app-dashboard-wallet',
  standalone: true,
  imports: [
    CommonModule,
    CardComponent,
    CopyTextComponent,
    CardButtonComponent
  ],
  templateUrl: './dashboard-general.component.html',
  animations: [
    trigger('fadeIn', [
      transition(':enter', [
        style({ 'opacity': 0 }),
        animate('0.3s', style({ 'opacity': 1 })),
      ])
    ]),
  ]
})
export class DashboardWalletComponent {

  public readonly balance: number = 2000
  public readonly lastTransaction: number = 123
  public readonly transactionsThisMonth: number = 151
  public readonly average: number = 28
  public readonly accountFullName: string = 'Ivanov Ivan Ivanovich'
  public readonly accountType: string = 'Individual'
  public readonly accountUuid: string = 'lsdfjslkfjlskkjf'
  public readonly accountPhoneNumber: string = '+123456789'

  @Input()
  public modalService: ModalService

  @Output()
  public tabHandler: EventEmitter<string> = new EventEmitter<string>()

  public changeTab(tab: string): void {
    this.tabHandler.emit(tab)
  }

  public getPhoneNumberLogoSrc() {
    if (window.matchMedia('(prefers-color-scheme: dark)').matches) {
      return '../assets/icons/phone-fill-dark.svg'
    } else {
      return '../assets/icons/phone-fill.svg'
    }
  }

  public getAccountLogoSrc() {
    if (window.matchMedia('(prefers-color-scheme: dark)').matches) {
      return '../assets/icons/user-shared-2-line-dark.svg'
    } else {
      return '../assets/icons/user-shared-2-line.svg'
    }
  }
}