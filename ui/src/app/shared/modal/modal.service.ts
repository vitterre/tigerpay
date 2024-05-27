import { Injectable } from '@angular/core'
import { BehaviorSubject } from 'rxjs'

@Injectable({
  providedIn: 'root'
})
export class ModalService {

  public isVisible$ = new BehaviorSubject<boolean>(false)

  constructor() { }

  public open() {
    this.isVisible$.next(true)
  }

  public close() {
    this.isVisible$.next(false)
  }
}
