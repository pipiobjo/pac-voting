import {it, inject, async, beforeEachProviders} from '@angular/core/testing';
import {BaseRequestOptions, Http, ResponseOptions, Response} from '@angular/http';
import {MockBackend} from '@angular/http/testing';
import {Title} from './title.service';
import {AuthService} from '../../shared';

describe('Title', () => {
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
    AuthService
  ]);


  it('should have http', inject([Title], (title) => {
    expect(!!title.http).toEqual(true);
  }));

  it('should get data from the server', async(inject([Title, MockBackend], (title, backend) => {
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

    return new Promise((pass, fail) => {
      title.getData().subscribe(
        (next) => {
          expect(console.log).toHaveBeenCalled();
          expect(next).toEqual({value: 'API SERVER IS ALIVE'});
          pass();
        }, (error) => {
          fail(error);
        }
      );

    });

  })));

});
