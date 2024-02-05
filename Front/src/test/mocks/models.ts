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
    "name": "Julian Verification",
    "rating": 3.0,
    "role": "TRUCKER",
}

export const publicationMock =  {
    "arrivalDate": "2024-03-11T22:47:57-03:00",
    "creator": "http://pawserver.it.itba.edu.ar/paw-2023a-08/api/users/23",
    "departureDate": "2024-02-13T22:47:57-03:00",
    "destination": "Chajari",
    "image": "http://pawserver.it.itba.edu.ar/paw-2023a-08/api/images/230",
    "origin": "Buenos Aires",
    "price": 10000,
    "self": "http://pawserver.it.itba.edu.ar/paw-2023a-08/api/trips/95",
    "tripId": 95,
    "type": "Refrigerated",
    "volume": 50,
    "weight": 780
}

export const offerMock = {
    "description": "asdfsdaf",
    "offerId": 12,
    "price": 12333,
    "maxPage":"0",
    "trip": "http://localhost:8080/api/trips/4",
    "user": "http://localhost:8080/api/users/50"
}

export const reviewMock = {
    "user" : "http://localhost:8080/api/users/50",
    "trip" : "http://localhost:8080/api/trips/4",
    "rating" : 3,
    "review" : "good",
    "maxPage" : "0"
}

