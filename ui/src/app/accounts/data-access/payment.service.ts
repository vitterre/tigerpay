import { HttpClient } from '@angular/common/http'
import { Injectable, signal, WritableSignal } from '@angular/core'
import { BehaviorSubject } from 'rxjs'
import { IPaymentAccountResponse } from './IPaymentAccountResponse'
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
      .get<Array<ITransferResponse>>(`http://localhost:7000/api/v1/payments/transfers?ledger=${this.getCurrentAccountState()?.ledger}`)
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

}