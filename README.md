#courses-service

RESTful service for listing courses and registered course participants

##Building

Gradle wrapper is included so the simplest thing to do is:
`./gradlew build`

##Running

To spawn an embedded Tomcat on port 8080:
`./gradlew run`

##API

###List courses

`GET /courses` - lists all available courses sorted alphabetically by course name
This endpoint also supports two optional query parameters:

`fromTime` - (long) lists courses starting after given timestamp, sorted by course start time in ascending order

`toTime` - (long) lists courses ending before given timestamp, sorted by course end time in descending order

If both of the optional parameters are present, the endpoint returns returns courses that start after given `fromTime` and end before given `toTime`

###List course participants

`GET /courses/{courseId}/participants` - lists participants registered for a given course

`GET /courses/{courseId}/participants.csv` - exports participants registered for a given course to an Excel readable CSV

##Implementation notes

This service demonstrates how to quickly bootstrap an end-to-end RESTful service using Spring Boot and friends. The service was test driven and also shows how the separate layers and components can be unit and integration tested.

###Frameworks
- Spring Boot - for bootstrapping/configuring/running the service
- Spring MVC - for the REST API
- Spring Data JPA - for the persistence layer
- HSQL DB - embedded in-memory database
- Flyway - database migrations

###Issues

- Dates are currently saved and exposed as longs. This may or may not be an issue. It would probably be nicer to persist them as proper Date objects.
- Test data that is used by component tests is currently inserted as part of Flyway migration. This means that modifying/adding/removing the test data can break the tests. A better solution would probably be to either have a separate set of migration scripts for the component tests than for local Tomcat profile. Or possibly delete/insert the test data programatically in the tests.