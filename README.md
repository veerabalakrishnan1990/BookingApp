#Train Ticket Booking App

##Prerequisites
Before running the Train Ticket Booking App, make sure you have Redis installed for persistence logic and distributed lock management.

APIs
1. Book ticket
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

2. View Receipt Details
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

4. Cancel ticket from Train
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

Running the App
1. Clone the repository: git clone <repository_url>
2. Start the server: mvn spring-boot:run
3. Access the APIs using the provided endpoints.
