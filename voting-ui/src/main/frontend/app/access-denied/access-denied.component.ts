import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'access-denied',
  styles: [require('./access-denied.component.scss')],
  template: require('./access-denied.component.html')
})
export class AccessDenied implements OnInit {

  constructor() {
  }


  ngOnInit():any {
    console.log('access denied');
  }

}
