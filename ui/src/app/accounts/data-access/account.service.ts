import { HttpClient } from '@angular/common/http'
import { Injectable, WritableSignal, signal } from '@angular/core'
import { BehaviorSubject } from 'rxjs'
import { IAccount } from './IAccount'

@Injectable({
  providedIn: 'any'
})
export class AccountService {

  private currentUserSignal: WritableSignal<any> =
    signal<IAccount | undefined | null>(undefined)
  public isLoaded = new BehaviorSubject<boolean>(false)

  constructor(
    private httpClient: HttpClient
  ) { }

  public updateState(account: IAccount) {
    this.currentUserSignal.set(account)
    this.isLoaded.next(true)
  }

  public getState() {
    return this.currentUserSignal()
  }
}