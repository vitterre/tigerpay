import { NgIf } from '@angular/common'
import { Component } from '@angular/core'
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms'
import { Router } from '@angular/router'
import { ButtonComponent } from '../../../shared/button/button.component'
import { ModalComponent } from '../../../shared/modal/modal.component'
import { ModalService } from '../../../shared/modal/modal.service'
import { TextInputComponent } from '../../../shared/text-input/text-input.component'
import { IAuthResponse } from '../../data-access/IAuthResponse'
import { ILogInRequest, Subject } from '../../data-access/ILogInRequest'
import { AuthService } from '../../data-access/auth.service'
import { ErrorComponent } from '../../ui/error/error.component'

@Component({
  selector: 'app-login',
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
  templateUrl: './accounts-login.component.html',
  styleUrl: './accounts-login.component.scss'
})
export class AccountsLoginComponent {

  public errorMessage: string

  public readonly form = new FormGroup({
    emailAddress: new FormControl<string | null>(null, [
      Validators.email
    ]),
    phoneNumber: new FormControl<string | null>(null, [
      Validators.pattern('(^$|[+][0-9]{11})')
    ]),
    password: new FormControl<string>('', [
      Validators.required
    ])
  })

  constructor(
    private authService: AuthService,
    private modalService: ModalService,
    private router: Router
  ) { }

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
    const subject = this.form.value.emailAddress as string !== '' && this.form.value.emailAddress as string !== null ? Subject.EMAIL_ADDRESS : Subject.PHONE_NUMBER
    const login = (subject === Subject.EMAIL_ADDRESS ? this.form.value.emailAddress : this.form.value.phoneNumber) as string
    const password = this.form.value.password as string

    const loginRequest: ILogInRequest = {
      subject: Subject[subject],
      login: login,
      password: password
    }

    console.log(JSON.stringify(loginRequest));
    
    this.authService.login(loginRequest)
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
