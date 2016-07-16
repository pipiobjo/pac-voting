import {Component, OnInit} from '@angular/core';

console.log('`About` component loaded asynchronously');

@Component({
  selector: 'not-found',
  styles: [require('./not-found.component.scss')],
  template: require('./not-found.component.html')
})
export class NotFound implements OnInit {

  constructor() {
  }


  ngOnInit():any {
    console.log('route not found');
  }

}
