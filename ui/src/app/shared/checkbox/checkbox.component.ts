import { Component, Input } from '@angular/core'
import { ControlContainer, FormGroupDirective, FormsModule, ReactiveFormsModule } from '@angular/forms'

@Component({
  selector: 'app-checkbox',
  standalone: true,
  imports: [FormsModule, ReactiveFormsModule],
  templateUrl: './checkbox.component.html',
  styleUrl: './checkbox.component.scss',
  viewProviders: [
    {
      provide: ControlContainer,
      useExisting: FormGroupDirective
    }
  ]
})
export class CheckboxComponent {
  
  @Input()
  public label: string

  @Input()
  public parentFormGroupName: string
}
