/*
 * TODO: ES5 for now until I make a webpack plugin for protractor
 */
describe('App', () => {

  beforeEach(() => {
    browser.get('/');
  });


  it('should have a title', () => {
    let subject = browser.getTitle();
    let result = 'Spring Angular 2 starter';
    expect(subject).toEqual(result);
  });

  it('should have main-content', () => {
    let subject = element(by.css('app .main-content')).isPresent();
    let result = true;
    expect(subject).toEqual(result);
  });

  it('should have navigation', () => {
    let subject = element(by.css('app .navigation')).isPresent();
    let result = true;
    expect(subject).toEqual(result);
  });

  it('should have <footer>', () => {
    let subject = element(by.css('app .footer md-card-title')).getText();
    let result = 'Spring Boot Angular 2 Webpack Starter';
    expect(subject).toEqual(result);
  });

});
