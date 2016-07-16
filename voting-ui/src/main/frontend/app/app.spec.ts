import {it, inject, beforeEachProviders} from '@angular/core/testing';
import {App} from './app.component';
import {AuthService} from './shared';
import {BaseRequestOptions, Http} from '@angular/http';
import {MockBackend} from '@angular/http/testing/mock_backend';
import {TEST_ROUTER_PROVIDERS} from './shared/testing';

// Load the implementations that should be tested

describe('App', () => {
  // provide our implementations or mocks to the dependency injector
  beforeEachProviders(() => [
    App,
    BaseRequestOptions,
    MockBackend,
    ...TEST_ROUTER_PROVIDERS,
    {
      provide: Http,
      useFactory: function (backend, defaultOptions) {
        return new Http(backend, defaultOptions);
      },
      deps: [MockBackend, BaseRequestOptions]
    },
    AuthService,
  ]);

  it('should have a url', inject([App], (app) => {
    expect(app.url).toEqual('https://github.com/kucharzyk');
  }));

});
