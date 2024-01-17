export class Offer {
    id: number;
    trip_id: number;
    user_id: number;
    name: string;
    description: string;
    price: number;
    counter_offer_id: number;
    

    constructor(id: number, trip_id: number, user_ud: number, name: string, description: string, price: number, counter_offer_id: number) {
        this.id = id;
        this.trip_id = trip_id;
        this.user_id = user_ud;
        this.name = name;
        this.description = description;
        this.price = price;
        this.counter_offer_id = counter_offer_id;
    }

    static offerFromJson(json: any): Offer {
        return new Offer(json.id, json.trip_id, json.user_id, json.name, json.description, json.price, json.counter_offer_id);
    }
    
    static offerToJson(offer: Offer): any {
        return {
            id: offer.id,
            trip_id: offer.trip_id,
            user_id: offer.user_id,
            name: offer.name,
            description: offer.description,
            price: offer.price,
            counter_offer_id: offer.counter_offer_id
        }
    }
}