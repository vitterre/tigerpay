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

@Component({
  selector: 'app-dashboard-wallet',
  standalone: true,
  imports: [
    CommonModule,
    CardComponent,
    CopyTextComponent,
    CardButtonComponent,
    DropdownComponent
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

  public readonly balance: number = 2000
  public readonly lastTransaction: number = 123
  public readonly transactionsThisMonth: number = 151
  public readonly average: number = 28
  public readonly accountFullName: string = 'Ivanov Ivan Ivanovich'
  public readonly accountType: string = 'Individual'
  public readonly accountUuid: string = 'lsdfjslkfjlskkjf'
  public readonly accountPhoneNumber: string = '+123456789'

  @Input()
  public accountItems: Array<IDropdownElement> = []
  
  @Input()
  public paymentService: PaymentService

  @Output()
  public tabHandler: EventEmitter<string> = new EventEmitter<string>()

  constructor(
    public accountService: AccountService,
    public httpClient: HttpClient,
    private currencyPipe: CurrencyPipe
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
}