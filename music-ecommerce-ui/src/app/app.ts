import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {TopNavbar} from './components/navbar/top-navbar/top-navbar';
import {CategoryNavbar} from './components/navbar/category-navbar/category-navbar';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, TopNavbar, CategoryNavbar],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected title = 'music-ecommerce-ui';
}
