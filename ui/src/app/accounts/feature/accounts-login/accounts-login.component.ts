import { NgIf } from '@angular/common'
import { Component } from '@angular/core'
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms'
import { ButtonComponent } from '../../../shared/button/button.component'
import { TextInputComponent } from '../../../shared/text-input/text-input.component'
import { AuthService } from '../../data-access/auth.service'

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    FormsModule,
    ReactiveFormsModule,
    NgIf,
    ButtonComponent,
    TextInputComponent
  ],
  templateUrl: './accounts-login.component.html',
  styleUrl: './accounts-login.component.scss'
})
export class AccountsLoginComponent {
  public readonly form = new FormGroup({
    emailAddress: new FormControl<string>('', [
      Validators.required,
      Validators.email
    ]),
    password: new FormControl<string>('', [
      Validators.required
    ])
  })

  constructor(private authService: AuthService) { }

  get emailAddress() {
    return this.form.controls.emailAddress as FormControl
  }

  get password() {
    return this.form.controls.password as FormControl
  }

  public submit() {
    this.authService.login({
      emailAddress: this.form.value.emailAddress as string,
      password: this.form.value.password as string
    })
  }


}
