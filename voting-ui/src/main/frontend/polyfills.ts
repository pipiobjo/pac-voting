// Ccre.js
import 'core-js/es6';
import 'core-js/es7/reflect';
import 'ts-helpers';

// Zone.js
require('zone.js/dist/zone');

// Typescript emit helpers polyfill

if ('production' !== ENV) {
  Error['stackTraceLimit'] = Infinity;
  require('zone.js/dist/long-stack-trace-zone');
}

