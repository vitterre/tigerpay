import { animate, style, transition, trigger } from '@angular/animations'
import { CommonModule, CurrencyPipe } from '@angular/common'
import { HttpClient } from '@angular/common/http'
import { Component, EventEmitter, Input, Output } from '@angular/core'
import { AccountService } from '../../../accounts/data-access/account.service'
import { PaymentService } from '../../../accounts/data-access/payment.service'
import { CopyTextComponent } from '../../../accounts/ui/copy-text/copy-text.component'
import { CardButtonComponent } from '../../../shared/card-button/card-button.component'
import { CardComponent } from '../../../shared/card/card.component'
import { DropdownComponent } from '../../../shared/dropdown/dropdown.component'
import { IDropdownElement } from '../../../shared/dropdown/IDropdownElement'
import { ModalComponent } from '../../../shared/modal/modal.component'
import { ModalService } from '../../../shared/modal/modal.service'
import { DashboardDepositComponent } from '../dashboard-deposit/dashboard-deposit.component'
import { DashboardWithdrawalComponent } from '../dashboard-withdrawal/dashboard-withdrawal.component'

@Component({
  selector: 'app-dashboard-wallet',
  standalone: true,
  imports: [
    CommonModule,
    CardComponent,
    CopyTextComponent,
    CardButtonComponent,
    DropdownComponent,
    ModalComponent,
    DashboardDepositComponent,
    DashboardWithdrawalComponent
  ],
  providers: [
    CurrencyPipe
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

  @Input()
  public accountItems: Array<IDropdownElement> = []

  @Output()
  public tabHandler: EventEmitter<string> = new EventEmitter<string>()

  public showDeposit = false
  public showWithdrawal = false

  constructor(
    public accountService: AccountService,
    public httpClient: HttpClient,
    public paymentService: PaymentService,
    public modalService: ModalService
  ) { }

  public onSelectItem(index: number) {
    this.paymentService.switchCurrentAccountState(index)
  }

  public changeTab(tab: string): void {
    this.tabHandler.emit(tab)
  }

  public getDepositIconSrc() {
    if (window.matchMedia('(prefers-color-scheme: dark)').matches) {
      return '../assets/icons/deposit-light.svg'
    } else {
      return '../assets/icons/deposit-dark.svg'
    }
  }

  public getWithdrawalIconSrc() {
    if (window.matchMedia('(prefers-color-scheme: dark)').matches) {
      return '../assets/icons/withdrawal-light.svg'
    } else {
      return '../assets/icons/withdrawal-dark.svg'
    }
  }

  public openDeposit() {
    this.showDeposit = true
    this.showWithdrawal = false
    this.modalService.open()
  }

  public openWithdrawal() {
    this.showDeposit = false
    this.showWithdrawal = true
    this.modalService.open()
  }
}