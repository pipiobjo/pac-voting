import {Injectable} from '@angular/core';
import {Http} from '@angular/http';
import {AuthService} from '../../shared';
import 'rxjs/Rx';

@Injectable()
export class SurveyService {
  value = 'Angular 2';

  constructor(public http:Http, public authService:AuthService) {
  }

  getData() {
    console.log('Survey.getData()');

    return this
      .http
      .get('/api/surveys', {headers: this.authService.getAuthorizationHeaders()})
      .map(res => res.json());
  }

}
