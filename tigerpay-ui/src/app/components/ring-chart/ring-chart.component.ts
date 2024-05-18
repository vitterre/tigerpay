import { AfterViewInit, Component, Input } from '@angular/core'
import { IRingChartData } from '../../models/components/IRingChartData'

@Component({
  selector: 'app-ring-chart',
  standalone: true,
  imports: [],
  templateUrl: './ring-chart.component.html',
  styleUrl: './ring-chart.component.scss'
})
export class RingChartComponent implements AfterViewInit {
  
  @Input()
  public name: string

  @Input()
  public data: IRingChartData[]

  @Input()
  public colors: string[]

  @Input()
  public radius: number = 80

  @Input()
  public ringRadius: number = this.radius * 0.8

  @Input()
  public padding: number = 5

  private chartContainer: HTMLElement
  private svg: SVGElement
  private centerX: number
  private centerY: number
  private totalValue: number
  private topicTitle: SVGTextElement
  private topicValue: SVGTextElement

  
  public ngAfterViewInit(): void {  
    this.chartContainer = document.getElementById(this.name)!
    
    this.initialize()
    this.drawChart()
  }

  private initialize(): void {
    this.createSVGElement()
    this.initializeTopic()
  }

  private createSVGElement() {
    this.svg = document.createElementNS('http://www.w3.org/2000/svg', 'svg')
    this.svg.setAttribute('id', this.name)
    this.svg.setAttribute('width', `${2 * this.radius + 2 * this.padding}`)
    this.svg.setAttribute('height', `${2 * this.radius + 2 * this.padding}`)

    this.centerX = this.radius + this.padding
    this.centerY = this.radius + this.padding

    // mask and filter
    const defs = document.createElementNS('http://www.w3.org/2000/svg', 'defs')

    const mask = document.createElementNS('http://www.w3.org/2000/svg', 'mask')
    mask.setAttribute('id', 'ringMask');

    const rect = document.createElementNS('http://www.w3.org/2000/svg', 'rect')
    rect.setAttribute('width', '100%')
    rect.setAttribute('height', '100%')
    rect.setAttribute('fill', 'white')
    mask.appendChild(rect)

    const circle = document.createElementNS('http://www.w3.org/2000/svg', 'circle');
    circle.setAttribute('cx', `${this.centerX}`);
    circle.setAttribute('cy', `${this.centerY}`);
    circle.setAttribute('r', `${this.ringRadius}`);
    circle.setAttribute('fill', 'black');
    mask.appendChild(circle);

    defs.appendChild(mask);

    this.svg.appendChild(defs)

    this.chartContainer.appendChild(this.svg)
  }

  private initializeTopic() {
    this.totalValue = this.data.reduce((acc, data) => acc + data.value, 0)
    this.topicValue = document.createElementNS('http://www.w3.org/2000/svg', 'text')
    this.topicValue.setAttribute('x', `${this.centerX}`)
    this.topicValue.setAttribute('y', `${this.centerY + 2}`)
    // this.topicValue.setAttribute('fill', window.matchMedia('(prefers-color-scheme: dark)').matches ? '#eee' : '#000')
    this.topicValue.setAttribute('text-anchor', 'middle')
    this.topicValue.setAttribute('font-size', '24')
    this.topicValue.textContent = `$${this.totalValue}`
    this.topicValue.classList.add('font-bold')
    this.topicValue.classList.add('dark:fill-white')
    this.topicValue.classList.add('fill-black')
    this.svg.appendChild(this.topicValue)

    this.topicTitle = document.createElementNS('http://www.w3.org/2000/svg', 'text')
    this.topicTitle.setAttribute('x', `${this.centerX}`)
    this.topicTitle.setAttribute('y', `${this.centerY + 16}`)
    this.topicTitle.setAttribute('fill', '#aaa')
    this.topicTitle.setAttribute('text-anchor', 'middle')
    this.topicTitle.setAttribute('font-size', '12')
    this.topicTitle.textContent = 'Total'
    this.svg.appendChild(this.topicTitle)
  }

  private drawChart(): void {
    let startAngle = -90// start from top
    let endAngle = 270

    this.data.forEach((data, index) => {
      const sliceAngle = this.data.length === 1 ? 359.999 : (360 * data.value) / this.totalValue
      endAngle = startAngle + sliceAngle

      // draw slice here
      this.drawSlice(this.centerX,this.centerY, this.radius,
        startAngle, endAngle, this.colors[index], index, data)
      
      // update start angle for the next slice
      startAngle = endAngle
    })

    const circle = document.createElementNS('http://www.w3.org/2000/svg', 'circle');
    circle.setAttribute('cx', `${this.centerX}`);
    circle.setAttribute('cy', `${this.centerY}`);
    circle.setAttribute('r', `${this.ringRadius}`);
    circle.setAttribute('fill', 'black');
    circle.setAttribute('opacity', '0');
    this.svg.appendChild(circle)

    // bring text to front
    this.svg.removeChild(this.topicTitle)
    this.svg.removeChild(this.topicValue)
    this.svg.appendChild(this.topicTitle)
    this.svg.appendChild(this.topicValue)
  }

  private drawSlice(
    centerX: number,
    centerY: number,
    radius: number,
    startAngle: number,
    endAngle: number,
    color: string,
    index: number,
    data: IRingChartData
  ): void {
    console.log(this.centerX, this.centerY, this.radius,
      startAngle, endAngle, this.colors[index], index, data)

      const originalTopicValue = this.topicValue.textContent
      const originalTopicLabel = this.topicTitle.textContent
  
      const largeArcFlag = endAngle - startAngle <= 180 ? '0' : '1'
          
      // actual slice
      const path = document.createElementNS('http://www.w3.org/2000/svg', 'path')
      const pathId = `slice-${index}-of-${this.name}`
      path.setAttribute('id', pathId)
      const pathData = this.calculatePathData(centerX, centerY, radius, startAngle, endAngle, largeArcFlag)
      path.setAttribute('d', pathData)
      path.setAttribute('fill', color)

      // hover effect
      path.style.transition = 'all 0.3s ease'
      const enlargedRadius = radius + this.padding

      path.addEventListener('mouseenter', () => {
        const hoverPathData = this.calculatePathData(centerX, centerY, enlargedRadius, startAngle, endAngle, largeArcFlag)
        path.setAttribute('d', hoverPathData)
        this.topicValue.textContent = `$${data.value}`
        this.topicTitle.textContent = `${data.label}`
      })

      path.addEventListener('mouseleave', () => {
        path.setAttribute('d', pathData)
        this.topicValue.textContent = `${originalTopicValue}`
        this.topicTitle.textContent = `${originalTopicLabel}`
      })

      path.setAttribute('mask', 'url(#ringMask)')
      this.svg.appendChild(path)
  }

  private calculatePathData(centerX: number, centerY: number, radius: number, startAngle: number, endAngle: number, largeArcFlag: string): string {
    return [
      `M ${centerX} ${centerY}`, // move to center
      `L ${centerX + radius * Math.cos((startAngle * Math.PI) / 180)}
         ${centerY + radius * Math.sin((startAngle * Math.PI) / 180)}`, // line to starting point
      `A ${radius} ${radius} 0 ${largeArcFlag} 1
         ${centerX + radius * Math.cos((endAngle * Math.PI) / 180)}
         ${centerY + radius * Math.sin((endAngle * Math.PI) / 180)}`, // draw arc
      'Z' // close path
    ].join(' ')
  }

}
