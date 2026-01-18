# Dormitory Room Booking System

This is a console-based application for managing dormitory rooms and student bookings.

## Features
* User Management: Secure user registration and login.
* Room Management: View available rooms and prices.
* Booking Logic: Automatic checks for room capacity to prevent overbooking.
* Roommate Lookup: View the list of occupants in a specific room. 

## Database Setup
1. Created a PostgreSQL database named `dorm_db`.
2. Run the following SQL script to create tables:

```sql
CREATE TABLE rooms (
    id SERIAL PRIMARY KEY,
    room_number INT NOT NULL,
    capacity INT NOT NULL,
    price_per_month DOUBLE PRECISION NOT NULL
);

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    surname VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    gender VARCHAR(10),
    room_id INT REFERENCES rooms(id)
);
```

# Project Structure
* data: Database connection logic.
* models: Models representing database tables (User, Room).
* repositories: CRUD operations and direct SQL queries.
* controllers: Business logic and input processing.
* Main.java: Entry point of the application.
* MyApplication: Frontend of the system, handles interaction between the user and the controllers.

> Repositories are the "hands" that go into the database, while Controllers are the "brain" of application.

# 
 
 Created by:
> Alibek Orazaly, Medina Sugirbay, Sanzhar Serikov
