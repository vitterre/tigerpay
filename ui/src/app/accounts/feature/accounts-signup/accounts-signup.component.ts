import { NgIf } from '@angular/common'
import { Component } from '@angular/core'
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms'
import { ButtonComponent } from '../../../shared/button/button.component'
import { TextInputComponent } from '../../../shared/text-input/text-input.component'
import { AuthService } from '../../data-access/auth.service'

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [
    FormsModule,
    ReactiveFormsModule,
    NgIf,
    ButtonComponent,
    TextInputComponent
  ],
  templateUrl: './accounts-signup.component.html',
  styleUrl: './accounts-signup.component.scss'
})
export class AccountsSignupComponent {
  public readonly form = new FormGroup({
    firstName: new FormControl<string>('', [
      Validators.required
    ]),
    lastName: new FormControl<string>('', [
      Validators.required
    ]),
    middleName: new FormControl<string>(''),
    emailAddress: new FormControl<string>('', [
      Validators.required,
      Validators.email
    ]),
    password: new FormControl<string>('', [
      Validators.required
    ])
  })

  constructor(private authService: AuthService) { }

  get firstName() {
    return this.form.controls.firstName as FormControl
  }

  get lastName() {
    return this.form.controls.lastName as FormControl
  }

  get middleName() {
    return this.form.controls.middleName as FormControl
  }

  get emailAddress() {
    return this.form.controls.emailAddress as FormControl
  }

  get password() {
    return this.form.controls.password as FormControl
  }

  public submit() {
    this.authService.signup({
      firstName: this.form.value.firstName as string,
      lastName: this.form.value.lastName as string,
      middleName: this.form.value.middleName as string,
      emailAddress: this.form.value.emailAddress as string,
      password: this.form.value.password as string,
    })
  }
}
