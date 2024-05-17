import { Component } from '@angular/core'
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms'
import { CheckboxComponent } from '../checkbox/checkbox.component'

@Component({
  selector: 'app-transfer-form',
  standalone: true,
  imports: [FormsModule, ReactiveFormsModule, CheckboxComponent],
  templateUrl: './transfer-form.component.html',
  styleUrl: './transfer-form.component.scss'
})
export class TransferFormComponent {
  public readonly form = new FormGroup({
    phoneNumber: new FormControl<string>(''),
    accountUuid: new FormControl<string>(''),
    sendBill: new FormControl<boolean>(true),
    amount: new FormControl<number>(0, [
      Validators.min(10)
    ])
  })

  get phoneNumber() {
    return this.form.controls.phoneNumber as FormControl
  }

  get accountUuid() {
    return this.form.controls.accountUuid as FormControl
  }

  get sendBill() {
    return this.form.controls.sendBill as FormControl
  }

  get amount() {
    return this.form.controls.amount as FormControl
  }

  public submit() {
    console.log({
      phoneNumber: this.form.value.phoneNumber as string,
      accountUuid: this.form.value.accountUuid as string,
      sendBill: this.form.value.sendBill as boolean,
      amount: this.form.value.amount as number,
    })
  }
}
