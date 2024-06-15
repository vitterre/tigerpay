import { CommonModule } from '@angular/common'
import { Component, Input } from '@angular/core'
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms'
import { ITransferRequest } from '../../../accounts/data-access/ITransferRequest'
import { PaymentService } from '../../../accounts/data-access/payment.service'
import { ButtonComponent } from '../../../shared/button/button.component'
import { CheckboxComponent } from '../../../shared/checkbox/checkbox.component'
import { IDropdownElement } from '../../../shared/dropdown/IDropdownElement'
import { TextInputComponent } from '../../../shared/text-input/text-input.component'
import { InputDropdownComponent } from '../../ui/input-dropdown/input-dropdown.component'

@Component({
  selector: 'app-dashboard-transfer-form',
  standalone: true,
  imports: [
    FormsModule,
    ReactiveFormsModule,
    CheckboxComponent,
    ButtonComponent,
    CommonModule,
    TextInputComponent,
    InputDropdownComponent
  ],
  templateUrl: './dashboard-transfer-form.component.html',
  styleUrl: './dashboard-transfer-form.component.scss'
})
export class DashboardTransferFormComponent {
  @Input()
  public accountItems: IDropdownElement[] = []

  public categories = [
    'TRANSFERS',
    'HOUSING',
    'UTILITIES',
    'CAFES',
    'TRANSPORT',
    'INVESTMENTS',
    'HEALTHCARE',
    'BEAUTY',
    'DEBT',
    'ENTERTAINMENT',
    'INSURANCE',
    'GROCERIES',
    'SALARY'
  ]

  public selectedCategoryIndex = 0

  public readonly form = new FormGroup({
    phoneNumber: new FormControl<string>(''),
    accountUuid: new FormControl<number | null>(null),
    sendBill: new FormControl<boolean>(true),
    amount: new FormControl<number | null>(null, [
      Validators.min(10)
    ]),
    category: new FormControl<string>('')
  })

  constructor(
    public paymentService: PaymentService
  ) { }

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

    const transferRequest: ITransferRequest = {
      receiverId: this.form.value.accountUuid as number,
      amount: this.form.value.amount as number,
      ledger: this.paymentService.getCurrentAccountState()?.ledger,
      code: this.categories[this.selectedCategoryIndex]
    }

    console.log(transferRequest)

    this.paymentService.transfer(transferRequest)
      .subscribe(
        () => {
          setTimeout(() => {
            console.log('success!')
            window.location.reload()
          }, 200)
        },
        (error: Error) => {
          console.log(error.message)
        }
      )

    // console.log({
    //   phoneNumber: this.form.value.phoneNumber as string,
    //   accountUuid: this.form.value.accountUuid as string,
    //   sendBill: this.form.value.sendBill as boolean,
    //   amount: this.form.value.amount as number,
    // })
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

  public mapCategories(categories: string[]) {
    return categories.flatMap(c => {
      return {
        label: c,
        value: '',
        sign: ''
      }
    })
  }

  public onSelectAccount(index: number) {
    this.paymentService.switchCurrentAccountState(index)
  }

  public onSelectCategory(index: number) {
    this.selectedCategoryIndex = index
  }
}
