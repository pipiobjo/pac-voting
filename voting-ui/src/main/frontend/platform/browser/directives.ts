import {PLATFORM_DIRECTIVES} from '@angular/core';
import {MATERIAL_DIRECTIVES} from './material2';
import {ROUTER_DIRECTIVES} from '@angular/router';
import {REACTIVE_FORM_DIRECTIVES} from '@angular/forms';

// application_directives: directives that are global through out the application
export const APPLICATION_DIRECTIVES = [
  ...MATERIAL_DIRECTIVES,
  ...ROUTER_DIRECTIVES,
  ...REACTIVE_FORM_DIRECTIVES
];

export const DIRECTIVES = [
  {
    provide: PLATFORM_DIRECTIVES,
    useValue: APPLICATION_DIRECTIVES,
    multi: true
  }
];
