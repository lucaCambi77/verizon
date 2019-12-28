
## Task ##

See VZC - Backend Exercise.pdf 

See uml diagram AppointmentsUML.jpg

## Install and run the application
To use the application you need java 8 installed in your machine. This is a spring boot application with embedded tomcat

There is a default profiles with which you can run the application

To install 

	- mvn clean install

To run  

	- mvn spring-boot:run

## Api

After the application is running, following api are exposed :

`` (POST) http://localhost:8080/attendee `` create new attendee, with example json

{
  "name" : "Nome",
  "surname" : "Cognome",
  "address" : {
    "street" : "Via via",
    "zipCode" : "1000",
    "city" : "Pistoia",
    "country" : "Italy"
  },
  "email" : "email@xxx.com",
  "_id" : null,
  "enabled" : true
}

`` (POST) http://localhost:8080/appointment `` create new appointment, with example json

{
  "type" : "MEETING",
  "_id" : null,
  "name" : "Meeting1",
  "day" : "2000-01-01",
  "time" : "00:00:00",
  "address" : {
    "street" : "Via ...",
    "zipCode" : "1001",
    "city" : "Pistoia",
    "country" : "Italy"
  },
  "confirmed" : true,
  "type" : "MEETING",
  "attendees" : [ ... attendees id ... ],
  "recurrentIntervalDays" : 0,
  "oneOff" : false
}

`` (PUT) http://localhost:8080/appointment `` update appointment

`` (DELETE) http://localhost:8080/appointment `` soft delete appointment

`` (GET) http://localhost:8080/appointment `` find all appointment

`` (GET) http://localhost:8080/appointment/{id} `` find all appointment by id

`` (GET) http://localhost:8080/appointment/day/{day} `` find all appointment by day

`` (GET) http://localhost:8080/appointment/attendeeByday `` find all appointment for an attendee by day, with parameter day and attendee
