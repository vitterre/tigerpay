import { NgIf } from '@angular/common'
import { Component } from '@angular/core'
import { CardComponent } from '../../components/card/card.component'
import { TabBarComponent } from '../../components/tab-bar/tab-bar.component'

@Component({
  selector: 'app-dashboard-page',
  standalone: true,
  imports: [TabBarComponent, CardComponent, NgIf],
  templateUrl: './dashboard-page.component.html',
  styleUrl: './dashboard-page.component.scss'
})
export class DashboardPageComponent {

  public readonly tabBarItems = [
    {
      id: 1,
      label: 'General'
    },
    {
      id: 2,
      label: 'Operations'
    },
    {
      id: 3,
      label: 'Payments'
    }
  ]
  public activeTab = this.tabBarItems[0]

  public changeTab(tab: any) {
    this.activeTab = tab
  }
}
