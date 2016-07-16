import {it, inject, describe, beforeEachProviders} from '@angular/core/testing';
import {BaseRequestOptions, Http, Response, ResponseOptions} from '@angular/http';
import {MockBackend} from '@angular/http/testing';
import {Home} from './home.component';
import {Title} from './shared';
import {AuthService} from '../shared';


// Load the implementations that should be tested

describe('Home', () => {
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
    Title,
    Home,
    AuthService
  ]);

  it('should have default data', inject([Home], (home) => {
    expect(home.data).toEqual({value: ''});
  }));

  it('should have a title', inject([Home], (home) => {
    expect(!!home.title).toEqual(true);
  }));

  it('should log ngOnInit', inject([Home, MockBackend], (home, backend) => {

    spyOn(console, 'log');
    expect(console.log).not.toHaveBeenCalled();

    var mockedResponse = new Response(
      new ResponseOptions({
          body: '{"value":"API SERVER IS ALIVE"}'
        }
      ));

    backend.connections.subscribe(connection => {
      connection.mockRespond(mockedResponse);
    });

    home.ngOnInit();


    expect(console.log).toHaveBeenCalled();
  }));

});
