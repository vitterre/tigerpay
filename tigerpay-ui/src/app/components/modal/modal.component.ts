import { animate, style, transition, trigger } from '@angular/animations'
import { CommonModule } from '@angular/common'
import { Component } from '@angular/core'
import { ModalService } from '../../services/modal.service'


@Component({
  selector: 'app-modal',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './modal.component.html',
  styleUrl: './modal.component.scss',
  animations: [
    trigger('modalAnimation', [
      transition(':enter', [
        style({ opacity: 0 }),
        animate('0.3s', style({ opacity: 1 })),
      ]),
      transition(':leave', [
        animate('0.3s', style({ opacity: 0 })),
      ]),
    ])
  ]
})
export class ModalComponent {

  constructor(public modalService: ModalService) {}
}
