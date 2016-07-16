export const ASYNC_ROUTES = {
  About: 'About',
  Playground: 'Playground',
  Crud: 'Crud'
};

export class LazyLoader {

  static lazyLoad(lazymodule:string):Promise<any> {

    var resolver = (resolve, reject) => {

      switch (lazymodule) {
        case ASYNC_ROUTES.About:
          require.ensure([], (require) => {
            resolve(require('../../../app/+about/index'));
          });
          break;
        case ASYNC_ROUTES.Playground:
          require.ensure([], (require) => {
            resolve(require('../../../app/+playground/index'));
          });
          break;
        case ASYNC_ROUTES.Crud:
          require.ensure([], (require) => {
            resolve(require('../../../app/+crud/index'));
          });
          break;
        default:
          reject();
      }
    };

    return new Promise<any>(resolver);
  }
}
