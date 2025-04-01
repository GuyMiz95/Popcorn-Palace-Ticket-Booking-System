# Instructions.md

## Popcorn Palace Ticket Booking System – Setup & Run Guide

---

## Required files and environment

- Java SDK 21 
- Maven 
- Docker 
- IDE – IntelliJ IDEA (optional)

---

## Setup Instructions
### for Windows (CMD/PowerShell)
#### 1. Clone the repository

```
git clone [repository-url]
cd TicketBookingSystem
```
#### 2. Run PostgreSQL DB using Docker
```
docker compose up -d
```

#### 3. Build project and run the app

```
mvnw.cmd spring-boot:run
```

#### 4. Run tests
```
mvnw.cmd test
```

### Using IntelliJ IDEA for all mentioned steps:
alternatively you can use the IDE to build the project:
#### 1. Clone the repository (using IDE terminal)
```
git clone [repository-url]
``` 
#### 2. Build project
1) file -> open -> choose downloaded repository as root folder
2) select Java 21 as Project SDK
#### 3. Run PostgreSQL DB using docker
Using same commands in cmd\terminal when found inside root folder (containing provided compose.yml)
#### 4. Run app 
do so by running "PopcornPalaceTicketBookingSystemApplication.java"
#### 5. Run unit tests
can do so by either running each of them individually or as 
whole by right-clicking the tests folder (src/test/java) and choose Run Tests.