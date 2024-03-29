export class Trip {
    arrivalDate: Date;
    departureDate: Date;
    creator: string;
    destination: string;
    image: string;
    licensePlate: string;
    origin: string;
    price: number;
    proposalCount: number;
    proposals: string;
    provider: string;
    providerConfirmation: boolean;
    providerSubmittedHisReview: boolean;
    self: string;
    tripId: number;
    trucker: string;
    truckerConfirmation: boolean;
    truckerSubmittedHisReview: boolean;
    type: string;
    volume: number;
    weight: number;
    maxPage: string;

    constructor(arrivalDate: Date, departureDate: Date, creator:string,destination: string, image: string, licensePlate: string, origin: string, price: number, proposalCount: number, proposals: string, provider: string, providerConfirmation: boolean, providerSubmittedHisReview: boolean, self: string, tripId: number, trucker: string, truckerConfirmation: boolean, truckerSubmittedHisReview: boolean, type: string, volume: number, weight: number, maxPage: string) {
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
        this.creator = creator;
        this.destination = destination;
        this.image = image;
        this.licensePlate = licensePlate;
        this.origin = origin;
        this.price = price;
        this.proposalCount = proposalCount;
        this.proposals = proposals;
        this.provider = provider;
        this.providerConfirmation = providerConfirmation;
        this.providerSubmittedHisReview = providerSubmittedHisReview;
        this.self = self;
        this.tripId = tripId;
        this.trucker = trucker;
        this.truckerConfirmation = truckerConfirmation;
        this.truckerSubmittedHisReview = truckerSubmittedHisReview;
        this.type = type;
        this.volume = volume;
        this.weight = weight;
        this.maxPage = maxPage
    }

    static tripFromJson(json: any): Trip {
        return new Trip(
            json.arrivalDate,
            json.departureDate,
            json.creator,
            json.destination,
            json.image,
            json.licensePlate,
            json.origin,
            json.price,
            json.proposalCount,
            json.proposals,
            json.provider,
            json.providerConfirmation,
            json.providerSubmittedHisReview,
            json.self,
            json.tripId,
            json.trucker,
            json.truckerConfirmation,
            json.truckerSubmittedHisReview,
            json.type,
            json.volume,
            json.weight,
            json.maxPage
        )
    }

    static tripToJson(trip: Trip): string {
        return JSON.stringify({
            "arrivalDate": trip.arrivalDate,
            "departureDate": trip.departureDate,
            "creator" : trip.creator,
            "destination": trip.destination,
            "image": trip.image,
            "licensePlate": trip.licensePlate,
            "origin": trip.origin,
            "price": trip.price,
            "proposalCount": trip.proposalCount,
            "proposals": trip.proposals,
            "provider": trip.provider,
            "providerConfirmation": trip.providerConfirmation,
            "providerSubmittedHisReview": trip.providerSubmittedHisReview,
            "self": trip.self,
            "tripId": trip.tripId,
            "trucker": trip.trucker,
            "truckerConfirmation": trip.truckerConfirmation,
            "truckerSubmittedHisReview": trip.truckerSubmittedHisReview,
            "type": trip.type,
            "volume": trip.volume,
            "weight": trip.weight
        })
    }
}