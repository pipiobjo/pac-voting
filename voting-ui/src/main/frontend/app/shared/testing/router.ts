import {Router, ActivatedRoute} from '@angular/router';
class MockRouter {
}
class MockActivatedRoute {
}

export const TEST_ROUTER_PROVIDERS = [
  {
    provide: Router,
    useClass: MockRouter
  },
  {
    provide: ActivatedRoute,
    useClass: MockActivatedRoute
  }
];
