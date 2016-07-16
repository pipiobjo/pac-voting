/*
 * Providers provided by Angular
 */
import {bootstrap} from '@angular/platform-browser-dynamic';
import {DIRECTIVES, PIPES, PROVIDERS, ENV_PROVIDERS} from './platform';
import {App} from './app';
import {decorateComponentRef} from './platform/environment';

/*
 * App Component
 * our top level component that holds all of our components
 */

/*
 * Load styles
 */
require('./scss/main.scss');


/*
 * Bootstrap our Angular app with a top level component `App` and inject
 * our Services and Providers into Angular's dependency injection
 */
export function main() {

  return bootstrap(App as any, [
    ...PROVIDERS,
    ...ENV_PROVIDERS,
    ...DIRECTIVES,
    ...PIPES
  ])
    .then(decorateComponentRef) //angular debug tools
    .catch(err => console.error(err));

}

/*
 * Hot Module Reload
 * experimental version by @gdi2290
 */

function bootstrapDomReady() {
  // bootstrap after document is ready
  return document.addEventListener('DOMContentLoaded', main);
}

if ('development' === ENV) {
  // activate hot module reload
  if (HMR) {
    if (document.readyState === 'complete') {
      main();
    } else {
      bootstrapDomReady();
    }
    module.hot.accept();
  } else {
    bootstrapDomReady();
  }
} else {
  bootstrapDomReady();
}
