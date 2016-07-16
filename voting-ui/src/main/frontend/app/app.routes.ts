import {RouterConfig} from '@angular/router';
import {Home} from './home';
import {Login} from './login';
import {ASYNC_ROUTES} from '../platform/browser/webpack/lazy-loader';
import {NotFound} from './not-found';
import {AccessDenied} from './access-denied/access-denied.component';
import {AuthenticatedGuard, AdminGuard, UnauthenticatedGuard} from './shared/guards';
import {AuthService} from './shared/auth/auth.service';

export const routes:RouterConfig = [
  {
    path: '',
    redirectTo: 'home'
  },
  {
    path: 'home',
    component: Home
  },
  {
    path: 'about',
    component: 'About'
  },
  {
    path: 'crud',
    component: 'Crud',
    canActivate: [AuthenticatedGuard, AdminGuard]
  },
  {
    path: 'playground',
    component: 'Playground'
  },
  {
    path: 'login',
    component: Login,
    canActivate: [UnauthenticatedGuard]
  },
  {
    path: 'accessDenied',
    component: AccessDenied
  },
  {
    path: '404',
    component: NotFound
  },
  {
    path: '**',
    redirectTo: '404',
    terminal: true
  },
];

export const asyncRoutes = {
  About: ASYNC_ROUTES.About,
  Playground: ASYNC_ROUTES.Playground,
  Crud: ASYNC_ROUTES.Crud
};

export const AUTH_PROVIDERS = [AuthService, AdminGuard, AuthenticatedGuard, UnauthenticatedGuard];


