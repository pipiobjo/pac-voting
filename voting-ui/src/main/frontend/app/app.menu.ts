export const APP_MENU:AppMenuItem[] = [

  {
    name: 'Home',
    description: 'Home page',
    icon: 'public',
    link: ['']
  }
];

export interface AppMenuItem {
  name:string;
  description:string;
  icon:string;
  link:string[];
  roles?:string[];
}
