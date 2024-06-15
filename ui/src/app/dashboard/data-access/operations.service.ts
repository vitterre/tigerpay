import { Injectable, signal, WritableSignal } from '@angular/core'
import { BehaviorSubject } from 'rxjs'
import { IAnalyticsTransfersResponse } from './IAnalyticsTransfersResponse'

@Injectable({
  providedIn: 'any'
})
export class OperationsService {

  public expenseDataSignal: WritableSignal<any> =
    signal<Array<IAnalyticsTransfersResponse> | undefined | null>(undefined)
  public isDataLoaded = new BehaviorSubject<boolean>(false)

  public updateDataState(expense: Array<IAnalyticsTransfersResponse>) {
    this.expenseDataSignal.set(expense)
    this.isDataLoaded.next(true)
  }

  public resetDataState() {
    this.expenseDataSignal.set(undefined)
    this.isDataLoaded.next(false)
  }
}