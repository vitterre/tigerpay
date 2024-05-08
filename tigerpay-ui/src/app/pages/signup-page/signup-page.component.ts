import { NgIf } from '@angular/common'
import { Component } from '@angular/core'
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms'
import { AuthService } from '../../services/auth.service'

@Component({
  selector: 'app-signup-page',
  standalone: true,
  imports: [
    FormsModule,
    ReactiveFormsModule,
    NgIf
  ],
  templateUrl: './signup-page.component.html',
  styleUrl: './signup-page.component.scss'
})
export class SignupPageComponent {
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
