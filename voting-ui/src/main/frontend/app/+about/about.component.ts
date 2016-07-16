import {Component, OnInit} from '@angular/core';

console.log('`About` component loaded asynchronously');

@Component({
  selector: 'about',
  styles: [require('./about.component.scss')],
  template: require('./about.component.html')
})
export class About implements OnInit {

  constructor() {
  }


  ngOnInit():any {
    console.log('hello `About` component');
  }

}
