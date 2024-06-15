import { HttpClient, HttpErrorResponse } from '@angular/common/http'
import { Injectable, signal, WritableSignal } from '@angular/core'
import { BehaviorSubject, catchError, throwError } from 'rxjs'
import { IDepositRequest } from '../../dashboard/data-access/IDepositRequest'
import { IWithdrawalRequest } from '../../dashboard/data-access/IWithdrawalRequest'
import { IPaymentAccountResponse } from './IPaymentAccountResponse'
import { ITransferRequest } from './ITransferRequest'
import { ITransferResponse } from './ITransferResponse'

@Injectable({
  providedIn: 'any'
})
export class PaymentService {

  private paymentAccountsSignal: WritableSignal<any> =
    signal<Array<IPaymentAccountResponse> | undefined | null>(undefined)
  public areAccountsLoaded = new BehaviorSubject<boolean>(false)

  private currentPaymentAccountIndexSignal: WritableSignal<any> =
    signal<number | undefined | null>(undefined)
  private currentPaymentAccountSignal: WritableSignal<any> =
    signal<IPaymentAccountResponse | undefined | null>(undefined)
  private transfersSignal: WritableSignal<any> =
    signal<Array<ITransferResponse> | undefined | null>(undefined)

  constructor(
    private httpClient: HttpClient
  ) {
    const index = localStorage.getItem('currentPaymentAccountIndex')

    if (index === null) {
      localStorage.setItem('currentPaymentAccountIndex', '0')
      this.currentPaymentAccountIndexSignal.set(0)
    } else { 
      this.currentPaymentAccountIndexSignal.set(Number(index))
    }
  }

  public updateAccountsState(paymentAccounts: Array<IPaymentAccountResponse>) {
    this.paymentAccountsSignal.set(paymentAccounts)
    this.currentPaymentAccountSignal.set(this.getAccountsState()[this.currentPaymentAccountIndexSignal()])
    this.areAccountsLoaded.next(true)
  }

  public switchCurrentAccountState(index: number) {
    this.currentPaymentAccountIndexSignal.set(index)
    this.currentPaymentAccountSignal.set(this.getAccountsState()[index])
    localStorage.setItem('currentPaymentAccountIndex', `${index}`)

    this.httpClient
      .get<Array<ITransferResponse>>(`http://localhost:7200/api/v1/payments/transfers?ledger=${this.getCurrentAccountState()?.ledger}`)
      .subscribe((response: Array<ITransferResponse>) => {
        this.updateTransfersState(response)
        console.log('updated')
      })
  }

  public updateTransfersState(transfers: Array<ITransferResponse>) {
    this.transfersSignal.set(transfers)
  }

  public getAccountsState() {
    return this.paymentAccountsSignal()
  }

  public getCurrentAccountState() {
    return this.currentPaymentAccountSignal()
  }

  public getCurrentAccountIndexState() {
    return this.currentPaymentAccountIndexSignal()
  }

  public getTransfersState() {
    return this.transfersSignal()
  }

  public transfer(transferRequest: ITransferRequest) {
    return this.httpClient
      .post('http://localhost:7200/api/v1/payments/transfers', transferRequest)
      .pipe(
        catchError((error: HttpErrorResponse) => {
          if (error.status === 400) {
            return throwError(() => new Error("You don't have enough balance"))
          } else if (error.status === 404) {
            return throwError(() => new Error("Receiver not found"))
          }
          return throwError(() => new Error(error.error.message))
        })
      )
  }

  public deposit(depositRequest: IDepositRequest) {
    return this.httpClient
      .post('http://localhost:7200/api/v1/payments/deposits', depositRequest)
  }

  public withdrawal(withdrawalRequest: IWithdrawalRequest) {
    return this.httpClient
      .post('http://localhost:7200/api/v1/payments/withdrawals', withdrawalRequest)
  }

}