  import {User} from './User';


export class Option {
  optionId: string;
  description: string;
  creator: User;
  voters: Array<User>;
}
