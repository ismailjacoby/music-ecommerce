import {Component, inject} from '@angular/core';
import {RouterLink} from '@angular/router';
import {Auth} from '../../../services/auth';

@Component({
  selector: 'app-top-navbar',
  standalone: true,
  imports: [
    RouterLink
  ],
  templateUrl: './top-navbar.html',
  styleUrl: './top-navbar.css'
})
export class TopNavbar {

  authService = inject(Auth);

  get isLoggedIn(){
    return this.authService.isLoggedIn();
  }

  logout(){
    this.authService.logout();
  }
}
