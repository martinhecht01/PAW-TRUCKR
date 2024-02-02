export class Offer {
    id: number;
    description: string;
    price: number;
    conterOfferUrl: string;
    userUrl: string;
    tripUrl: string;
    maxPage: string;


    
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

    constructor(id: number, description: string, price: number, conterOfferUrl: string, userUrl: string, tripUrl: string, maxPage: string) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.conterOfferUrl = conterOfferUrl;
        this.userUrl = userUrl;
        this.tripUrl = tripUrl;
        this.maxPage = maxPage;
    }

    /*

            "description": "asdfsdaf",
        "offerId": 12,
        "price": 12333,
        "self": "http://localhost:8080/api/offers/12",
        "trip": "http://localhost:8080/api/trips/4",
        "user": "http://localhost:8080/api/users/50"
        */

    static offerFromJson(json: any): Offer {
        return new Offer(
            json.offerId,
            json.description,
            json.price,
            json.counterOffer,
            json.user,
            json.trip,
            json.maxPage
        );
    }

    static offerToJson(offer: Offer): any {
        return {
            description: offer.description,
            price: offer.price,
            user: offer.userUrl,
            trip: offer.tripUrl,
            counterOffer: offer.conterOfferUrl
        }
    }
}