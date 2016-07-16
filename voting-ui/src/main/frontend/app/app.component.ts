import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {AuthService} from './shared';
import {APP_MENU, AppMenuItem} from './app.menu';

@Component({
  selector: 'app',
  pipes: [],
  providers: [],
  directives: [],
  styles: [require('./app.component.scss')],
  template: require('./app.component.html')
})
export class App implements OnInit {
  name = 'Spring Boot Angular 2 Webpack Starter';
  url = 'https://github.com/kucharzyk';
  loading:boolean = false;

  views:AppMenuItem[] = APP_MENU;

  constructor(public authService:AuthService, public router:Router) {
  }

  logMeOut():void {
    this.authService.logout();
    this.router.navigate(['']);
  }

  ngOnInit():void {
    console.log('app on init');
  }

}

