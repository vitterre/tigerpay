import { Component, Input } from '@angular/core'
import { ControlContainer, FormGroupDirective, FormsModule, ReactiveFormsModule } from '@angular/forms'

@Component({
  selector: 'app-text-input',
  standalone: true,
  imports: [FormsModule, ReactiveFormsModule],
  templateUrl: './text-input.component.html',
  styleUrl: './text-input.component.scss',
  viewProviders: [
    {
      provide: ControlContainer,
      useExisting: FormGroupDirective
    }
  ]
})
export class TextInputComponent {

  @Input()
  public placeHolder: string

  @Input()
  public parentFormGroupName: string

}
