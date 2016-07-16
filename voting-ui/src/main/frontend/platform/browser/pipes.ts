import {PLATFORM_PIPES} from '@angular/core';
import {MATERIAL_PIPES} from './material2';

// application_pipes: pipes that are global through out the application
export const APPLICATION_PIPES = [];

export const PIPES = [
  ...MATERIAL_PIPES,
  {
    provide: PLATFORM_PIPES,
    useValue: APPLICATION_PIPES,
    multi: true
  }
];
