import {it, inject, beforeEachProviders} from '@angular/core/testing';
import {BaseRequestOptions, Http} from '@angular/http';
import {MockBackend} from '@angular/http/testing';
import {AuthService} from '../shared';
import {Crud} from './crud.component';

// Load the implementations that should be tested

describe('Crud', () => {
  // provide our implementations or mocks to the dependency injector
  beforeEachProviders(() => [
    BaseRequestOptions,
    MockBackend,
    {
      provide: Http,
      useFactory: function (backend, defaultOptions) {
        return new Http(backend, defaultOptions);
      },
      deps: [MockBackend, BaseRequestOptions]
    },
    AuthService,
    Crud
  ]);

  it('should log ngOnInit', inject([Crud], (crud) => {
    spyOn(console, 'log');
    expect(console.log).not.toHaveBeenCalled();

    crud.ngOnInit();
    expect(console.log).toHaveBeenCalled();
  }));

});
