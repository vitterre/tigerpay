import { CommonModule } from '@angular/common'
import { Component } from '@angular/core'
import { CardButtonComponent } from '../../components/card-button/card-button.component'
import { CardComponent } from '../../components/card/card.component'
import { CopyTextComponent } from '../../components/copy-text/copy-text.component'
import { InputButtonComponent } from '../../components/input-button/input-button.component'
import { ModalComponent } from '../../components/modal/modal.component'
import { RingChartComponent } from '../../components/ring-chart/ring-chart.component'
import { TabBarComponent } from '../../components/tab-bar/tab-bar.component'
import { TagComponent } from '../../components/tag/tag.component'
import { TransferFormComponent } from '../../components/transfer-form/transfer-form.component'
import { CopyDirective } from '../../directives/copy.directive'
import { IRingChartData } from '../../models/components/IRingChartData'
import { ModalService } from '../../services/modal.service'

@Component({
  selector: 'app-dashboard-page',
  standalone: true,
  imports: [TabBarComponent, CardComponent, CopyTextComponent, CardButtonComponent, CommonModule, TagComponent, CopyDirective, RingChartComponent, InputButtonComponent, ModalComponent, TransferFormComponent],
  templateUrl: './dashboard-page.component.html',
  styleUrl: './dashboard-page.component.scss'
})
export class DashboardPageComponent {

  public readonly accountUuid = '7b2057df-1379-4d08-89b4-6e62cf447f22'
  public readonly accountPhoneNumber = '+123456789'

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

  private expenseData = [
    { category: 'Housing', atThisMonth: 124 },
    { category: 'Transport', atThisMonth: 28 },
    { category: 'Investments', atThisMonth: 120 },
    { category: 'Entertainment', atThisMonth: 90 },
    { category: 'Groceries', atThisMonth: 230 }
  ]
  public expenseChartData: IRingChartData[]
  public expenseChartColors: string[]
  public expenseTagData: { tag: string, extra: string }[]
  public expenseTagColors: string[]

  private revenueData = [
    { category: 'Salary', atThisMonth: 3728 },
    { category: 'Investments', atThisMonth: 2382 }
  ]
  public revenueChartData: IRingChartData[]
  public revenueChartColors: string[]
  public revenueTagData: { tag: string, extra: string }[]
  public revenueTagColors: string[]
  
  constructor(public modalService: ModalService) {
    this.expenseChartData = this.expenseData.flatMap(s => {
      return { label: s.category, value: s.atThisMonth }
    })
    this.expenseTagData = this.expenseData.flatMap(s => {
      return { tag: s.category, extra: `$${s.atThisMonth}` }
    })
    this.expenseChartColors = this.expenseData.flatMap(s => {
      return this.color(s.category)
    })
    this.expenseTagColors = this.expenseData.flatMap(s => {
      return this.colorFromCategory(s.category)
    })

    this.revenueChartData = this.revenueData.flatMap(s => {
      return { label: s.category, value: s.atThisMonth }
    })
    this.revenueTagData = this.revenueData.flatMap(s => {
      return { tag: s.category, extra: `$${s.atThisMonth}` }
    })
    this.revenueChartColors = this.revenueData.flatMap(s => {
      return this.color(s.category)
    })
    this.revenueTagColors = this.revenueData.flatMap(s => {
      return this.colorFromCategory(s.category)
    })
  }

  private colorFromCategory(category: string) {
    switch (category) {
      case 'Housing': return 'red'
      case 'Utilities': return 'orange'
      case 'Fastfood': return 'amber'
      case 'Transport': return 'yellow'
      case 'Investments': return 'lime'
      case 'Healthcare': return 'green'
      case 'Beauty': return 'emerald'
      case 'Education': return 'teal'
      case 'Debt': return 'cyan'
      case 'Entertainment': return 'fuchsia'
      case 'Insurance': return 'blue'
      case 'Groceries': return 'indigo'
      case 'Taxes': return 'purple'
      case 'Salary': return 'sky'
      default: return 'light-grey'
    }
  }

  private color(label: string): string {
    switch (label) {
      case 'Housing': return 'var(--red)'
      case 'Utilities': return 'var(--orange)'
      case 'Fastfood': return 'var(--amber)'
      case 'Transport': return 'var(--yellow)'
      case 'Investments': return 'var(--lime)'
      case 'Healthcare': return 'var(--green)'
      case 'Beauty': return 'var(--emerald)'
      case 'Education': return 'var(--teal)'
      case 'Debt': return 'var(--cyan)'
      case 'Entertainment': return 'var(--fuchsia)'
      case 'Insurance': return 'var(--blue)'
      case 'Groceries': return 'var(--indigo)'
      case 'Taxes': return 'var(--purple)'
      case 'Salary': return 'var(--sky)'
      default: return 'var(--light-grey)'
    }
  }

  public changeTab(tab: any) {
    this.activeTab = tab
  }
}
