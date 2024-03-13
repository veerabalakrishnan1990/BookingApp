# Train Ticket Booking App

## Prerequisites
Before running the Train Ticket Booking App, make sure you have Redis installed for persistence logic and distributed lock management.

APIs
## 1. Book ticket
  Endpoint: POST /api/booking/book

```json
Request Body:

{
  "userId": "1",
  "trainId": "2",
  "seatNumber": "1A",
  "departureStation": "NewYork",
  "arrivalStation": "Wash"
}

Response:
Booking request processed for seat: 1A
```

## 2. View Receipt Details
Endpoint: GET /api/booking/bookingInfo

```json
Request Body:

{
    "userId": "1",
    "trainId":"2"
}

Response:
[
    {
        "seatNumber": "1A",
        "state": "BookedState",
        "booking": {
            "id": null,
            "user": "1",
            "departureStation": "NewYork",
            "trainId": "2",
            "arrivalStation": "Wash",
            "travelDate": null,
            "pricePaid": 0,
            "seatNumber": "1A"
        }
    }
]
```

## 3. Cancel ticket from Train
Endpoint: POST /api/booking/cancel

```json
Request:

{
    "userId": "1",
    "trainId": "2",
    "seatNumber": "1A"
}

Response: HTTP Status 200 OK
```

## Running the App
1. Clone the repository: gh repo clone veerabalakrishnan1990/BookingApp
2. Start the server: mvn spring-boot:run
3. Access the APIs using the provided endpoints.

## Test coverage
The bookingApp has been thoroughly tested, achieving an impressive test coverage of almost 80%. This means that a substantial portion of the codebase has been scrutinized by automated tests, ensuring its reliability and robustness. With such comprehensive test coverage, we can have greater confidence in the stability and correctness of the application, as it has been rigorously vetted against various scenarios and edge cases. This commitment to testing helps uphold the quality standards of the bookingApp, ultimately resulting in a more dependable and resilient software product.

<img width="593" alt="image" src="https://github.com/veerabalakrishnan1990/BookingApp/assets/131878410/00c3e117-c674-4f3b-af66-5f3d598e0d70">

<img width="442" alt="image" src="https://github.com/veerabalakrishnan1990/BookingApp/assets/131878410/5eb8cacc-32b0-43e7-bdad-0897cc910f43">

