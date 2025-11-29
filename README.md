📘 Pet Health Tracker
Object-Oriented Programming + Database + GenAI Integration
Spring Boot · JPA · H2 Database
Overview

The Pet Health Tracker is a Spring Boot application that demonstrates object-oriented design, relational database modeling, CRUD operations, and optional integration with Generative AI tools.

The system manages two types of data:

Pets – basic information about each pet

Vet Visits – individual medical visits linked to a specific pet

This project fulfills the assignment requirements by:

Designing classes that map to database tables

Interacting with a relational database through an object-oriented language (Java)

Demonstrating full Create, Read, Update, Delete (CRUD) functionality

Using Generative AI as an aid in design, sample data creation, and code review

Technologies Used

Java 17+

Spring Boot

Spring Web

Spring Data JPA

H2 in-memory database

Jackson (JSON serialization)

Project Structure
src/main/java/com/example/pethealth/
│
├── model/
│   ├── Pet.java
│   └── VetVisit.java
│
├── repository/
│   ├── PetRepository.java
│   └── VetVisitRepository.java
│
├── service/
│   ├── PetService.java
│   └── VetVisitService.java
│
└── controller/
    ├── PetController.java
    └── VetVisitController.java

How to Run the Application

Open the project in IntelliJ.

Run the main Spring Boot application class.
👉 http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:petdb
\
