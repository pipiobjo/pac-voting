# Overview

Angular2 Frontend für die Verwendung der voting rest api. 
Authentifizierung über OAuth2
 
 
# Artefacts

* Module Bundler
	** Webpack (https://webpack.github.io) 
 
* Testautomation 
	** Karma (https://karma-runner.github.io/1.0/index.html)  
 
 
 # DEV
 ## ui server in development mode
 mvn spring-boot:run -P dev -Dspring.profiles.active=dev
 
 ##  webpack development server
 npm run server
 
 ## Unit Tests
 npm run test
 
 ## e2e tests
 npm run e2e