
Monitoring is based on an externel MessagingService, called rabbitmq from here https://www.rabbitmq.com/download.html. It is fully integrated in the microservice stack. 
With Eureka Service Detection. 
For now there is no persistence layer for the messages. So they are not stored, but can be configured https://www.rabbitmq.com/persistence-conf.html 




To show monitoring results: 

Open 
http://localhost:8989/hystrix/

And Enter: 
http://localhost:8989/turbine.stream

Or Just Call
http://localhost:8989/hystrix/monitor?stream=http%3A%2F%2Flocalhost%3A8989%2Fturbine.stream

