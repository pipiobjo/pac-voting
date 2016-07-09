==Voting Convention Dokumentation==

* Alle Artefakte müssen voting-application-parent als Maven Parent Projekt beinhalten. 


== Versionierung == 

[AA.BB.CC-SNAPSHOT</version>]

AA : Major Changes
BB : Minor Changes
CC : Bugfixes / Hotfixes
-SNAPSHOT : für Dev Artefakte


== Service Registry ==

http://localhost:8761/



== Voting Persistence ==

http://localhost:8180 



== Monitoring == 

http://localhost:7979/

Das Monitoring wird über die Hystrix Commands realisiert. 

Auf der fachlichen API kann per Annotation (@HystrixCommand) ein Methode überwacht werden. 

Hierfür ist folgende dependency notwendig: 
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-hystrix</artifactId>
</dependency>

Dies erstellt einen service bereit {host}:{port}/hystrix
Die dort sichtbaren Daten werden vom support-monitor Artefakt gesammelt und mit Hilfe des Turbine Aggregators dargestellt
