import { animate, style, transition, trigger } from '@angular/animations'
import { CommonModule } from '@angular/common'
import { Component } from '@angular/core'
import { CardComponent } from '../../../shared/card/card.component'
import { TagComponent } from '../../../shared/tag/tag.component'
import { IRingChartData } from '../../ui/ring-chart/IRingChartData'
import { RingChartComponent } from '../../ui/ring-chart/ring-chart.component'

@Component({
  selector: 'app-dashboard-operations',
  standalone: true,
  imports: [
    CommonModule,
    CardComponent,
    RingChartComponent,
    TagComponent
  ],
  templateUrl: './dashboard-operations.component.html',
  animations: [
    trigger('fadeIn', [
      transition(':enter', [
        style({ 'opacity': 0 }),
        animate('0.3s', style({ 'opacity': 1 })),
      ])
    ]),
  ]
})
export class DashboardOperationsComponent {

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

  constructor() {
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
}