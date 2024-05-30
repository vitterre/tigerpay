import { Component } from '@angular/core'
import { RouterOutlet } from '@angular/router'
import { NgxSkeletonLoaderModule } from 'ngx-skeleton-loader'
import { NavigationComponent } from './shared/navigation/navigation.component'

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, NavigationComponent, NgxSkeletonLoaderModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'tigerpay-ui'
}
