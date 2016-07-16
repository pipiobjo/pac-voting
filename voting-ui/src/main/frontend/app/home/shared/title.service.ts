import {Injectable} from '@angular/core';
import {Http} from '@angular/http';
import {AuthService} from '../../shared';
import 'rxjs/Rx';

@Injectable()
export class Title {
  value = 'Angular 2';

  constructor(public http:Http, public authService:AuthService) {
  }

  getData() {
    console.log('Title.getData()');

    return this
      .http
      .get('/api/title', {headers: this.authService.getAuthorizationHeaders()})
      .map(res => res.json());
  }

}
