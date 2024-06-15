import { CommonModule } from '@angular/common'
import { Component, Input } from '@angular/core'
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms'
import { PaymentService } from '../../../accounts/data-access/payment.service'
import { ButtonComponent } from '../../../shared/button/button.component'
import { DropdownComponent } from '../../../shared/dropdown/dropdown.component'
import { IDropdownElement } from '../../../shared/dropdown/IDropdownElement'
import { TextInputComponent } from '../../../shared/text-input/text-input.component'
import { IWithdrawalRequest } from '../../data-access/IWithdrawalRequest'
import { InputDropdownComponent } from '../../ui/input-dropdown/input-dropdown.component'

@Component({
  selector: 'app-dashboard-withdrawal',
  standalone: true,
  imports: [
    FormsModule,
    ReactiveFormsModule,
    CommonModule,
    ButtonComponent,
    TextInputComponent,
    DropdownComponent,
    InputDropdownComponent
  ],
  templateUrl: './dashboard-withdrawal.component.html',
  styleUrl: './dashboard-withdrawal.component.scss'
})
export class DashboardWithdrawalComponent {

  @Input()
  public accountItems: IDropdownElement[] = []

  public readonly form = new FormGroup({
    amount: new FormControl<number | null>(null, [
      Validators.min(10)
    ])
  })

  constructor(
    public paymentService: PaymentService
  ) {
    console.log(this.accountItems);
    
  }

  public submit() {
    const depositRequest: IWithdrawalRequest = {
      amount: this.form.value.amount as number,
      ledger: this.paymentService.getCurrentAccountState()?.ledger
    }

    console.log(depositRequest)

    this.paymentService.withdrawal(depositRequest)
      .subscribe(() => {
        window.location.reload()
      })
  }

  public map(elements: IDropdownElement[]) {
    return elements.flatMap(el => {
      return {
        label: el.label,
        value: el.secondary,
        sign: el.label === 'USD' ? '$' : '€'
      }
    })
  }

  public onSelectAccount(index: number) {
    this.paymentService.switchCurrentAccountState(index)
  }
}
