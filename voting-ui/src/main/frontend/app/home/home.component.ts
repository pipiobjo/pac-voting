import {Component} from '@angular/core';
import {Survey} from './shared';
import {SurveyService} from './shared';
import {XLarge, AuthService} from '../shared';

@Component({
  selector: 'home',
  providers: [
    SurveyService
  ],
  directives: [XLarge as any],
  pipes: [],
  styles: [require('./home.component.scss')],
  template: require('./home.component.html')
})
export class Home {
  surveys: Survey[];        
  errorMessage: string;


  constructor(public surveyService:SurveyService, public authService:AuthService) {
  }

  ngOnInit() {
    console.log('hello `Home` component');
    this.surveyService.getData()
        .subscribe(
            surveys => this.surveys = surveys,
            error =>  this.errorMessage = <any>error
        );
  }

}
