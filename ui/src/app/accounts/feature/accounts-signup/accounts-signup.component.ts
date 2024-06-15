import { NgIf } from '@angular/common'
import { Component } from '@angular/core'
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms'
import { Router } from '@angular/router'
import { ButtonComponent } from '../../../shared/button/button.component'
import { ModalComponent } from '../../../shared/modal/modal.component'
import { ModalService } from '../../../shared/modal/modal.service'
import { TextInputComponent } from '../../../shared/text-input/text-input.component'
import { IAuthResponse } from '../../data-access/IAuthResponse'
import { AuthService } from '../../data-access/auth.service'
import { ErrorComponent } from '../../ui/error/error.component'

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [
    FormsModule,
    ReactiveFormsModule,
    NgIf,
    ButtonComponent,
    TextInputComponent,
    ModalComponent,
    ErrorComponent
  ],
  templateUrl: './accounts-signup.component.html',
  styleUrl: './accounts-signup.component.scss'
})
export class AccountsSignupComponent {
  
  public errorMessage: string

  public readonly form = new FormGroup({
    firstName: new FormControl<string>('', [
      Validators.required,
      Validators.minLength(1),
      Validators.maxLength(50)
    ]),
    lastName: new FormControl<string>('', [
      Validators.required,
      Validators.minLength(1),
      Validators.maxLength(50)
    ]),
    middleName: new FormControl<string | null>(null, [
      Validators.minLength(1),
      Validators.maxLength(50)
    ]),
    emailAddress: new FormControl<string>('', [
      Validators.required,
      Validators.email
    ]),
    phoneNumber: new FormControl<string>('', [
      Validators.required,
      Validators.pattern('(^$|[+][0-9]{11})')
    ]),
    password: new FormControl<string>('', [
      Validators.required,
      Validators.pattern('^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&()^])[A-Za-z\\d@$!%*#?&()^]{8,}$')
    ])
  })

  constructor(
    private authService: AuthService,
    private modalService: ModalService,
    private router: Router
  ) { }

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

  get phoneNumber() {
    return this.form.controls.phoneNumber as FormControl
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
      phoneNumber: this.form.value.phoneNumber as string,
      password: this.form.value.password as string,
    })
      .subscribe(
        (response: IAuthResponse) => {
          localStorage.setItem('accessToken', response.accessToken)
          localStorage.setItem('refreshToken', response.refreshToken)
          setTimeout(() => {
            this.router.navigateByUrl('/dashboard')
          }, 100);
        },
        (error: Error) => {
          this.handleError(error)
        }
      )
  }

  private handleError(error: Error) {
    this.errorMessage = error.message
    this.modalService.open()
  }
}
