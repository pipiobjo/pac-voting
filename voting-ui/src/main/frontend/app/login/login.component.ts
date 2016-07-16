import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {AuthService} from '../shared';

console.log('`Login` component loaded asynchronously');

@Component({
  selector: 'login',
  styles: [require('./login.component.scss')],
  template: require('./login.component.html')
})
export class Login implements OnInit {

  username = 'admin';
  password = 'xxxxxx';
  message = '';

  constructor(public authService:AuthService, public router:Router) {
  }

  logMeIn() {
    console.log('LogMeIn');
    this.authService
      .authenticate(this.username, this.password)
      .catch(errorMessage => this.message = errorMessage)
      .then(() => {
        if (this.authService.isAuthenticated()) {
          this.router.navigate(['']);
        }
      });

  }

  ngOnInit():any {
    console.log('hello `Login` component');
  }

}
