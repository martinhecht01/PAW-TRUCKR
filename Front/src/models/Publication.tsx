export class Publication {
    /*
{
        "arrivalDate": "2024-02-05T14:30:00-03:00",
        "creator": "http://localhost:8080/webapp_war/users/6",
        "departureDate": "2024-01-31T12:00:00-03:00",
        "destination": "Rosario",
        "image": "http://localhost:8080/webapp_war/images/46",
        "origin": "Azul",
        "price": 5000,
        "self": "http://localhost:8080/webapp_war/trips/45",
        "tripId": 45,
        "type": "Refrigerated",
        "volume": 100,
        "weight": 500
    }
    */ 
    arrivalDate: Date;
    creator: string;
    departureDate: Date;
    destination: string;
    image: string;
    origin: string;
    price: number;
    self: string;
    tripId: number;
    type: string;
    volume: number;
    weight: number;

    constructor(arrivalDate: Date, creator: string, departureDate: Date, destination: string, image: string, origin: string, price: number, self: string, tripId: number, type: string, volume: number, weight: number) {
        this.arrivalDate = arrivalDate;
        this.creator = creator;
        this.departureDate = departureDate;
        this.destination = destination;
        this.image = image;
        this.origin = origin;
        this.price = price;
        this.self = self;
        this.tripId = tripId;
        this.type = type;
        this.volume = volume;
        this.weight = weight;
    }

    static publicationFromJson(json: any): Publication {
        return new Publication(
            json.arrivalDate,
            json.creator,
            json.departureDate,
            json.destination,
            json.image,
            json.origin,
            json.price,
            json.self,
            json.tripId,
            json.type,
            json.volume,
            json.weight
        );
    }

    static publicationToJson(publication: Publication): string {
        return JSON.stringify(publication);
    }
}