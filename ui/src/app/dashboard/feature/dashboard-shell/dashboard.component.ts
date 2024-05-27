import { CommonModule } from '@angular/common'
import { Component } from '@angular/core'
import { ModalComponent } from '../../../shared/modal/modal.component'
import { ModalService } from '../../../shared/modal/modal.service'
import { TabBarComponent } from '../../../shared/tab-bar/tab-bar.component'
import { DashboardWalletComponent } from '../dashboard-general/dashboard-general.component'
import { DashboardOperationsComponent } from '../dashboard-operations/dashboard-operations.component'
import { DashboardPaymentsComponent } from '../dashboard-payments/dashboard-payments.component'
import { DashboardTransferFormComponent } from '../dashboard-transfer/dashboard-transfer-form.component'

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [
    CommonModule,
    DashboardWalletComponent,
    DashboardOperationsComponent,
    DashboardPaymentsComponent,
    DashboardTransferFormComponent,
    TabBarComponent,
    ModalComponent
  ],
  templateUrl: './dashboard.component.html'
})
export class DashboardComponent {

  public readonly tabBarItems: Array<string> = [
    'General',
    'Operations',
    'Payments'
  ]

  public activeTab: string = this.tabBarItems[0]

  public changeTab(tab: string) {
    this.activeTab = tab
  }

  constructor(public modalService: ModalService) {}
}