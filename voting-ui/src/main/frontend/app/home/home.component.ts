import {Component} from '@angular/core';
import {Title} from './shared';
import {XLarge, AuthService} from '../shared';

@Component({
  selector: 'home',
  providers: [
    Title
  ],
  directives: [XLarge as any],
  pipes: [],
  styles: [require('./home.component.scss')],
  template: require('./home.component.html')
})
export class Home {
  angularLogo = 'assets/img/angular-logo.png';

  data = {value: ''};

  constructor(public title:Title, public authService:AuthService) {
  }

  ngOnInit() {
    console.log('hello `Home` component');
    this.title.getData().subscribe(data => this.data = data);
  }

}
