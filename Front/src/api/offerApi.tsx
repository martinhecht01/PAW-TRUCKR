import {Offer} from "../models/Offer.tsx";
import api from "./config";
import {getToken} from "./userApi";

export async function acceptOffer(offer: Offer): Promise<Offer> {
    const response = await api.put(`/offers/${offer.id}`);
    return Offer.offerFromJson(response.data);
}

export async function getOffersByTrip(id: number): Promise<Offer[]> {
    const response = await api.get(`/offers?tripId=${id}`);

    const toRet = []
    for (const offer of response.data) {
        toRet.push(Offer.offerFromJson(offer))
    }

    return toRet;
}

export async function getOffersByUser(id: number): Promise<Offer[]> {
    const response = await api.get('/offers?userId=' + id,{
        headers:{
            'Authorization': `Bearer ${getToken()}`,
            "Accept": "application/vnd.offerList.v1+json"
        }
    });

    const toRet = [];
    for (const offer of response.data) {
        toRet.push(Offer.offerFromJson(offer))
    }

    return toRet;
}

export async function getOffer(offerId : number) : Promise<Offer> {
    const response = await api.get(`/offers/${offerId}`);
    return Offer.offerFromJson(response.data);
}

export async function createOffer(tripId: number, price: number, description: string): Promise<Offer> {
    const response = await api.post(`/offers`, 
        {
            price: price,
            description: description,
            tripId: tripId
        },
        {
            headers:{
                'Authorization': `Bearer ${getToken()}`,
                'Accept': 'application/vnd.offer.v1+json',
                'Content-Type': 'application/vnd.offer.v1+json'
            },
        }
    );
    return Offer.offerFromJson(response.data);
}

export async function createCounterOffer(tripId: number, price: number, description: string, parentOfferId: number): Promise<Offer> {
    const response = await api.post(`/offers`, 
        {
            price: price,
            description: description,
            parent_offer_id: parentOfferId,
            tripId: tripId
        },
        {
            headers: {
                'Authorization': `Bearer ${getToken()}`,
                'Accept': 'application/vnd.offer.v1+json',
                'Content-Type': 'application/vnd.offer.v1+json'
            }
        }
    );
    return Offer.offerFromJson(response.data);
}
    