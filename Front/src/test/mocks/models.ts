export const alertMock = {
    cargoType:"Refrigerated",
    city:"Buenos Aires",
    fromDate: "2024-03-01T12:00:00-03:00",
    id: 7,
    maxVolume: 200,
    maxWeight: 200,
    self: "http://localhost:8080/api/alerts/7",
    toDate: "2024-04-01T12:00:00-03:00",
    user: "http://localhost:8080/api/users/1"
}

export const userMock = {
    "completedTripsCount": 10,
    "cuit": "20-43724688-3",
    "id": 44,
    "image": "http://localhost:8080/api/images/1",
    "name": "Julian Verification",
    "rating": 3.0,
    "reviews": "http://localhost:8080/api/reviews/?userId=44",
    "role": "TRUCKER",
    "self": "http://localhost:8080/api/users/44"
}

export const tripMock = {
    "arrivalDate": "2024-03-01T12:00:00-03:00",
    "cargoType": "Refrigerated",
    "departureDate": "2024-03-01T12:00:00-03:00",
    "destination": "Buenos Aires",
    "id": 7,
    "maxVolume": 200,
    "maxWeight": 200,
    "origin": "Buenos Aires",
    "self": "http://localhost:8080/api/trips/7",
    "status": "COMPLETED",
    "trucker": "http://localhost:8080/api/users/44"

}


export const offerMock = {
    "description": "asdfsdaf",
    "offerId": 12,
    "price": 12333,
    "self": "http://localhost:8080/api/offers/12",
    "trip": "http://localhost:8080/api/trips/4",
    "user": "http://localhost:8080/api/users/50"
}

export const reviewMock = {
    "user" : "http://localhost:8080/api/users/50",
    "trip" : "http://localhost:8080/api/trips/4",
    "rating" : 3,
    "review" : "good"
}

