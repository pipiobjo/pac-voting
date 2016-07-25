import {Injectable} from '@angular/core';
import {User} from './User';
import {Option} from './Option';


export class Survey {
    description: string;
    surveyId: string;
    title: string;
    creator: User;
    options: Array<Option>;
}

