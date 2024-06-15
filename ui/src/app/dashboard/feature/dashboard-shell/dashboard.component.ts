import { CommonModule, CurrencyPipe } from '@angular/common'
import { HttpClient } from '@angular/common/http'
import { Component, OnInit } from '@angular/core'
import { AccountService } from '../../../accounts/data-access/account.service'
import { IAccount } from '../../../accounts/data-access/IAccount'
import { IPaymentAccountResponse } from '../../../accounts/data-access/IPaymentAccountResponse'
import { ITransferResponse } from '../../../accounts/data-access/ITransferResponse'
import { PaymentService } from '../../../accounts/data-access/payment.service'
import { IDropdownElement } from '../../../shared/dropdown/IDropdownElement'
import { ModalComponent } from '../../../shared/modal/modal.component'
import { ModalService } from '../../../shared/modal/modal.service'
import { TabBarComponent } from '../../../shared/tab-bar/tab-bar.component'
import { DashboardWalletComponent } from '../dashboard-general/dashboard-general.component'
import { DashboardOperationsComponent } from '../dashboard-operations/dashboard-operations.component'
import { DashboardPaymentsComponent } from '../dashboard-payments/dashboard-payments.component'
import { DashboardTransferFormComponent } from '../dashboard-transfer/dashboard-transfer-form.component'

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [
    CommonModule,
    DashboardWalletComponent,
    DashboardOperationsComponent,
    DashboardPaymentsComponent,
    DashboardTransferFormComponent,
    TabBarComponent,
    ModalComponent
  ],
  providers: [
    CurrencyPipe
  ],
  templateUrl: './dashboard.component.html'
})
export class DashboardComponent implements OnInit {

  public readonly tabBarItems: Array<string> = [
    'General',
    'Operations',
    'Payments'
  ]
  public accountItems: Array<IDropdownElement> = []
  public activeTab: string = this.tabBarItems[0]

  public changeTab(tab: string) {
    this.activeTab = tab
  }

  constructor(
    public modalService: ModalService,
    public accountService: AccountService,
    public paymentService: PaymentService,
    public httpClient: HttpClient,
    private currencyPipe: CurrencyPipe
  ) { }

  public ngOnInit(): void {
    console.log('Init dashboard shell')
    this.httpClient
      .get<IAccount>('http://localhost:7100/api/v1/accounts/me')
      .subscribe((response: IAccount) => {
        setTimeout(() => {
          this.accountService.updateState(response)
        }, 900)
      })
    this.httpClient
      .get<Array<IPaymentAccountResponse>>('http://localhost:7200/api/v1/payments/accounts')
      .subscribe((response: Array<IPaymentAccountResponse>) => {
        setTimeout(() => {
          this.paymentService.updateAccountsState(response)

          const accounts: Array<IPaymentAccountResponse> = this.paymentService.getAccountsState()
          accounts.forEach(element => {
            this.accountItems.push({
              label: `${element.ledger}`,
              secondary: `${this.currencyPipe.transform(element.balance, element.ledger)}`
            })
          });

          this.httpClient
            .get<Array<ITransferResponse>>(`http://localhost:7200/api/v1/payments/transfers?ledger=${this.paymentService.getCurrentAccountState()?.ledger}`)
            .subscribe((response: Array<ITransferResponse>) => {
              this.paymentService.updateTransfersState(response)
            })
        }, 900)
      })
  }
}