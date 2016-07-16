import {Component} from '@angular/core';
import {AppComponent} from './heroes/app.component.ts';

console.log('`Playground` component loaded asynchronously...');

@Component({
  selector: 'playground',
  template: `<div><my-app></my-app></div>`,
  directives: [AppComponent]
})
export class Playground {
  constructor() {
  }

  ngOnInit() {
    console.log('hello `Playground` component');
  }

}
