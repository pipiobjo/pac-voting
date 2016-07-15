
# Development

HAL Browser
http://localhost:8180/

Mit ihm kann die REST-API entsprechend manuelle verwendet werden. 


Weitere Dokumentation bringt Swagger
http://localhost:8180/swagger-ui.html


## REST-API



Leeren der DB per REST-API
http://localhost:8180/clearDatabase


Erzeugung von Beispiel Daten per REST-APi
http://localhost:8180/createRandomSurvey




http://localhost:8180/surveys/search


http://localhost:8180/resource


http://localhost:8180/votes




Setup a ne4j installation


# Environment Settings

## Start der Applikation 

```bash 
	java -jar -Dspring.profiles.active=dev persistence-voting-*.jar
```

Starten mit einer älteren Java Version -> fail fast

```
	/jdk1.7.0_79/jre/bin/java -jar persistence-voting-0.0.1-SNAPSHOT.jar 
	Exception in thread "main" java.lang.UnsupportedClassVersionError: com/prodyna/pac/main/PersistenceApplication : Unsupported major.minor version 52.0

```



Um eine spezifische Umgebung auszuwählen ist der ein einfacher Parameter notwendig


```bash 
	java -jar -Dspring.profiles.active=dev persistence-voting-*.jar
```



# Logging Konfiguration
Konfiguration ist über logback.xml möglich

Die Konfiguration der einzelnen Loglevel lässt sich über die Property-Files steuern



# Todo

* Fail Fast wenn keine ENV gesetzt ist
* Publish ENV Configuration, damit andere Applikationen den Status überprüfen können
* Loglevel Konfiguration per support-config ermöglichen 

 

# Weitere Hilfen

Spring Data Neo4j
http://docs.spring.io/spring-data/data-neo4j/docs/4.1.2.RELEASE/reference/html/

HA ENV Dokumentation
http://docs.spring.io/spring-data/neo4j/docs/current/reference/html/#_dependencies_for_spring_data_neo4j_4_1


Help Hatoeas Rest API
https://spring.io/blog/2014/07/14/spring-data-rest-now-comes-with-alps-metadata

Diskussion about HATOAS and Swagger
https://github.com/springfox/springfox/issues/238


