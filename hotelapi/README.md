# Hotel API

A Spring Boot REST API for hotel room reservations, including room availability checks and booking management.

## Features

- Room and reservation management
- Check available rooms for a given period
- Book rooms with check-in and check-out dates
- Data persistence with SQL database

## Technologies

- Java
- Spring Boot
- Maven
- SQL (JPA/Hibernate)

## Getting Started

### Prerequisites

- Java 17+
- Maven 3.8+
- SQL database (e.g., MySQL, PostgreSQL)

### Setup

1. Clone the repository: `git clone https://github.com/yourusername/hotelapi.git cd hotelapi`

3. Configure your database in `src/main/resources/application.properties`.

3. Build and run the application:
`mvn spring-boot:run`

## API Endpoints

- `GET /quartos/disponiveis?checkin=YYYY-MM-DD&checkout=YYYY-MM-DD`  
  List available rooms for the given period.

- `POST /reservas`  
  Create a new reservation.

- `GET /quartos/{id}/disponivel?checkin=YYYY-MM-DD&checkout=YYYY-MM-DD`  
  Check if a specific room is available.

## License

MIT