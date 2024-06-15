import { animate, style, transition, trigger } from '@angular/animations'
import { CommonModule, CurrencyPipe } from '@angular/common'
import { HttpClient } from '@angular/common/http'
import { Component, Input, OnDestroy, OnInit } from '@angular/core'
import { PaymentService } from '../../../accounts/data-access/payment.service'
import { CardComponent } from '../../../shared/card/card.component'
import { DropdownComponent } from '../../../shared/dropdown/dropdown.component'
import { IDropdownElement } from '../../../shared/dropdown/IDropdownElement'
import { TagComponent } from '../../../shared/tag/tag.component'
import { IAnalyticsTransfersRequest } from '../../data-access/IAnalyticsTransfersRequest'
import { IAnalyticsTransfersResponse } from '../../data-access/IAnalyticsTransfersResponse'
import { OperationsService } from '../../data-access/operations.service'
import { IRingChartData } from '../../ui/ring-chart/IRingChartData'
import { RingChartComponent } from '../../ui/ring-chart/ring-chart.component'

@Component({
  selector: 'app-dashboard-operations',
  standalone: true,
  imports: [
    CommonModule,
    CardComponent,
    RingChartComponent,
    TagComponent,
    DropdownComponent
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
export class DashboardOperationsComponent implements OnInit, OnDestroy {

  private expenseData = [
    { category: 'Housing', atThisMonth: 124 },
    { category: 'Transport', atThisMonth: 28 },
    { category: 'Investments', atThisMonth: 120 },
    { category: 'Entertainment', atThisMonth: 90 },
    { category: 'Groceries', atThisMonth: 230 }
  ]
  public expenseChartData: IRingChartData[]
  public expenseChartTotalValue: string | null
  public expenseChartColors: string[]
  public expenseTagData: { tag: string, extra: string }[]
  public expenseTagColors: string[]

  private revenueData = [
    { category: 'Salary', atThisMonth: 3728 },
    { category: 'Investments', atThisMonth: 2382 }
  ]
  public revenueChartData: IRingChartData[]
  public revenueChartTotalValue: string | null
  public revenueChartColors: string[]
  public revenueTagData: { tag: string, extra: string }[]
  public revenueTagColors: string[]

  public periodItems: IDropdownElement[] = [
    {
      label: 'Last week',
      secondary: ''
    },
    {
      label: 'Last month',
      secondary: ''
    },
    {
      label: 'Last three months',
      secondary: ''
    },
  ]

  @Input()
  public accountItems: IDropdownElement[] = []

  public currentPeriod: string = 'LAST_WEEK'

  public onSelectPeriod(index: number) {
    switch (this.periodItems[index].label) {
      case 'Last week': this.currentPeriod = 'LAST_WEEK'; break
      case 'Last month': this.currentPeriod = 'LAST_MONTH'; break
      case 'Last three months': this.currentPeriod = 'LAST_THREE_MONTHS'; break
    }
    this.ngOnDestroy()
    this.ngOnInit()
  }

  public onSelectAccount(index: number) {
    this.paymentService.switchCurrentAccountState(index)
    this.ngOnDestroy()
    this.ngOnInit()
  }

  public ngOnDestroy(): void {
    this.operationsService.resetDataState()
  }

  public ngOnInit(): void {
    console.log('init dashboard operations component page');
    console.log('PERIOD: ' + this.currentPeriod)
    
    const analyticsTransfersRequest: IAnalyticsTransfersRequest = {
      currency: this.paymentService.getCurrentAccountState() !== undefined ? this.paymentService.getCurrentAccountState().ledger : 'USD',
      period: this.currentPeriod
    }
    
    this.httpClient
      .post<Array<IAnalyticsTransfersResponse[]>>('http://localhost:7300/api/v1/analytics/categories', analyticsTransfersRequest)
      .subscribe((response: Array<IAnalyticsTransfersResponse[]>) => {
        // here must be set timeout + ring chart lazy loading
        // anyway
        setTimeout(() => {
          this.operationsService.updateDataState(response[0])
  
          this.expenseChartData = response[0].flatMap(s => {
            return {
              label: s.category,
              value: s.total,
              alternativeValueLabel:  this.currencyPipe.transform(s.total, this.paymentService.getCurrentAccountState().ledger) ?? `${s.total}`
            }
          })
          this.expenseChartTotalValue =
            this.currencyPipe.transform(
              this.expenseChartData.reduce((acc, data) => acc + data.value, 0).toFixed(2),
              this.paymentService.getCurrentAccountState().ledger
            )
          this.expenseTagData = response[0].flatMap(s => {
            return {
              tag: s.category,
              extra: this.currencyPipe.transform(s.total, this.paymentService.getCurrentAccountState().ledger) ?? `${s.total}`
            }
          })
          this.expenseChartColors = response[0].flatMap(s => {
            return this.color(s.category)
          })
          this.expenseTagColors = response[0].flatMap(s => {
            return this.colorFromCategory(s.category)
          })

          this.revenueChartData = response[1].flatMap(s => {
            return {
              label: s.category,
              value: s.total,
              alternativeValueLabel:  this.currencyPipe.transform(s.total, this.paymentService.getCurrentAccountState().ledger) ?? `${s.total}`
            }
          })
          this.revenueChartTotalValue =
            this.currencyPipe.transform(
              this.revenueChartData.reduce((acc, data) => acc + data.value, 0).toFixed(2),
              this.paymentService.getCurrentAccountState().ledger
            )
          this.revenueTagData = response[1].flatMap(s => {
            return {
              tag: s.category,
              extra: this.currencyPipe.transform(s.total, this.paymentService.getCurrentAccountState().ledger) ?? `${s.total}`
            }
          })
          this.revenueChartColors = response[1].flatMap(s => {
            return this.color(s.category)
          })
          this.revenueTagColors = response[1].flatMap(s => {
            return this.colorFromCategory(s.category)
          })
        }, 300)
      })
  }


  constructor(
    private httpClient: HttpClient,
    public operationsService: OperationsService,
    public paymentService: PaymentService,
    private currencyPipe: CurrencyPipe
  ) { }

  private colorFromCategory(category: string) {
    switch (category) {
      case 'WITHDRAWAL': return 'red'
      case 'TRANSFERS': return 'fuchsia'
      case 'DEPOSIT': return 'amber'
      case 'HOUSING': return 'yellow'
      case 'UTILITIES': return 'lime'
      case 'CAFES': return 'green'
      case 'TRANSPORT': return 'emerald'
      case 'INVESTMENTS': return 'teal'
      case 'HEALTHCARE': return 'cyan'
      case 'BEAUTY': return 'fuchsia'
      case 'DEBT': return 'blue'
      case 'ENTERTAINMENT': return 'indigo'
      case 'INSURANCE': return 'purple'
      case 'GROCERIES': return 'sky'
      default: return 'light-grey'
    }
  }

  private color(label: string): string {
    switch (label) {
      case 'WITHDRAWAL': return 'var(--red)'
      case 'TRANSFERS': return 'var(--fuchsia)'
      case 'DEPOSIT': return 'var(--amber)'
      case 'HOUSING': return 'var(--yellow)'
      case 'UTILITIES': return 'var(--lime)'
      case 'CAFES': return 'var(--green)'
      case 'TRANSPORT': return 'var(--emerald)'
      case 'INVESTMENTS': return 'var(--teal)'
      case 'HEALTHCARE': return 'var(--cyan)'
      case 'BEAUTY': return 'var(--fuchsia)'
      case 'DEBT': return 'var(--blue)'
      case 'ENTERTAINMENT': return 'var(--indigo)'
      case 'INSURANCE': return 'var(--purple)'
      case 'GROCERIES': return 'var(--sky)'
      default: return 'var(--light-grey)'
    }
  }
}