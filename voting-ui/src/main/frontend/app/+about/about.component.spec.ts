import {it, inject, describe, beforeEachProviders} from '@angular/core/testing';
import {About} from './about.component';

// Load the implementations that should be tested

describe('About', () => {
  // provide our implementations or mocks to the dependency injector
  beforeEachProviders(() => [
    About
  ]);

  it('should log ngOnInit', inject([About], (about) => {
    spyOn(console, 'log');
    expect(console.log).not.toHaveBeenCalled();

    about.ngOnInit();
    expect(console.log).toHaveBeenCalled();
  }));

});
