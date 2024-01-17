export class Trip {
    tripId: number;
    provider: number;
    trucker: number;
    licensePlate: string;
    weight: number;
    volume: number;
    departureDate: Date;
    arrivalDate: Date;
    origin: string;
    destination: string;
    type: string;
    price: number;
    truckerConfirmation: boolean;
    providerConfirmation: boolean;
    confirmationDate: Date;
    imageUrl: number;
    

    constructor(tripId: number, provider: number, trucker: number, licensePlate: string, weight: number, volume: number, departureDate: Date, arrivalDate: Date, origin: string, destination: string, type: string, price: number, truckerConfirmation: boolean, providerConfirmation: boolean, confirmationDate: Date, imageUrl: number) {
        this.tripId = tripId;
        this.provider = provider;
        this.trucker = trucker;
        this.licensePlate = licensePlate;
        this.weight = weight;
        this.volume = volume;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.origin = origin;
        this.destination = destination;
        this.type = type;
        this.price = price;
        this.truckerConfirmation = truckerConfirmation;
        this.providerConfirmation = providerConfirmation;
        this.confirmationDate = confirmationDate;
        this.imageUrl = imageUrl;
    }

    static tripFromJson(json: any): Trip {
        return new Trip(json.tripId, json.provider, json.trucker, json.licensePlate, json.weight, json.volume, json.departureDate, json.arrivalDate, json.origin, json.destination, json.type, json.price, json.truckerConfirmation, json.providerConfirmation, json.confirmationDate, json.imageUrl);
    }

    static tripToJson(trip: Trip): any {
        return {
            tripId: trip.tripId,
            provider: trip.provider,
            trucker: trip.trucker,
            licensePlate: trip.licensePlate,
            weight: trip.weight,
            volume: trip.volume,
            departureDate: trip.departureDate,
            arrivalDate: trip.arrivalDate,
            origin: trip.origin,
            destination: trip.destination,
            type: trip.type,
            price: trip.price,
            truckerConfirmation: trip.truckerConfirmation,
            providerConfirmation: trip.providerConfirmation,
            confirmationDate: trip.confirmationDate,
            imageUrl: trip.imageUrl
        }
    }
}