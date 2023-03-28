# Parking App

The Parking App is an application that simulates the car parking. The number of parking spaces & the charge per hour is configurable.

## Features

The Parking App includes the following features:

- Park the car
- Unpark the car
- Await until a free space is found

## Application setup & Installation

### Software Pre-requisites
- Java
- Maven

### Running the application
- Checkout the project
- Build the application: cd to the project's directory and run `mvn clean install`
- Test the application using: `mvn test`

## Assumptions
- Multiple cars can come in at the same time & look for free parking space
- Customer will be charged for a minimum of 1 hour once the car is parked. Number of hours will be ceiled to the next hour.
- A car will be allocated the very first parking space