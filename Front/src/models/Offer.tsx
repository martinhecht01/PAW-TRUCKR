export class Offer {
    id: number;
    description: string;
    price: number;
    conterOfferUrl: string;
    userUrl: string;
    tripUrl: string;


    
    /*
{
    "counterOffer": "http://localhost:8080/webapp_war/offers/14",
    "description": "te lo llevo",
    "price": 20000000,
    "proposalId": 13,
    "self": "http://localhost:8080/webapp_war/offers/13",
    "trip": "http://localhost:8080/webapp_war/trips/10",
    "user": "http://localhost:8080/webapp_war/users/6"
}
    */

    constructor(id: number, description: string, price: number, conterOfferUrl: string, userUrl: string, tripUrl: string) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.conterOfferUrl = conterOfferUrl;
        this.userUrl = userUrl;
        this.tripUrl = tripUrl;
    }

    static offerFromJson(json: any): Offer {
        return new Offer(
            json.proposal_id,
            json.description,
            json.price,
            json.counterOffer,
            json.user,
            json.trip
        );
    }

    static offerToJson(offer: Offer): any {
        return {
            id: offer.id,
            description: offer.description,
            price: offer.price,
            counterOffer: offer.conterOfferUrl,
            user: offer.userUrl,
            trip: offer.tripUrl
        };
    }
}