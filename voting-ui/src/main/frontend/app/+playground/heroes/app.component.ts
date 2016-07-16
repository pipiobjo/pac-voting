import {Component, OnInit} from '@angular/core';
import {Hero} from './hero';
import {HeroDetailComponent} from './hero-detail.component.ts';
import {HeroService} from './hero.service.ts';

@Component({
  selector: 'my-app',
  template: `
    <div style="text-align: center">
    <h1>{{title}}</h1>
    <h2>My Heroes</h2>
    </div>
    <ul class="heroes" style="width: 100%">
      <li *ngFor="let hero of heroes"
        [class.selected]="hero === selectedHero"
        (click)="onSelect(hero)">
        <span class="badge">{{hero.id}}</span> {{hero.name}}
      </li>
    </ul>
    <my-hero-detail [hero]="selectedHero"></my-hero-detail>


  `,
  styles: [require('./app.component.scss')],
  directives: [HeroDetailComponent],
  providers: [HeroService]
})
export class AppComponent implements OnInit {
  title = 'Tour of Heroes';
  heroes:Hero[];
  selectedHero:Hero;

  constructor(private _heroService:HeroService) {
  }

  getHeroes() {
    this._heroService.getHeroes().then(heroes => this.heroes = heroes);
  }

  ngOnInit() {
    this.getHeroes();
  }

  onSelect(hero:Hero) {
    this.selectedHero = hero;
  }
}

