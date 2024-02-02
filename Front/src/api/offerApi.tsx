import {Offer} from "../models/Offer.tsx";
import api from "./config";
import { getMaxPage } from "./paginationHelper.tsx";
import {getToken} from "./userApi";

export async function acceptOffer(id: string, action: 'ACCEPT' | 'REJECT'): Promise<Offer> {
    const response = await api.patch(`/offers/${id}`, {
        action: action
    },
    {
        headers: {
            'Authorization': `Bearer ${getToken()}`,
            'Content-Type': 'application/vnd.offer.v1+json',
        }
    }
    );
    return Offer.offerFromJson(response.data);
}

export async function getOffersByTrip(id: string, page: string, pageSize: string): Promise<Offer[]> {
    const response = await api.get(`/offers?tripId=${id}&page=${page}&pageSize=${pageSize}`, {
        headers: {
            'Authorization': `Bearer ${getToken()}`,
        }
    });

    const toRet = []
    for (const offer of response.data) {
        toRet.push(Offer.offerFromJson(offer))
    }

    if(response.status === 200)
        toRet[0].maxPage = getMaxPage(response.headers['link']).toString();

    return toRet;
}

export async function getOffersByUser(id: number, page: string, pageSize: string): Promise<Offer[]> {
    const response = await api.get(`/offers?userId=${id}&page=${page}&pageSize=${pageSize}`,{
        headers:{
            'Authorization': `Bearer ${getToken()}`,
            "Accept": "application/vnd.offerList.v1+json"
        }
    });

    const toRet = [];
    for (const offer of response.data) {
        toRet.push(Offer.offerFromJson(offer))
    }

    if(response.status === 200)
        toRet[0].maxPage = getMaxPage(response.headers['link']).toString();

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

export async function deleteOffer(offerId: number): Promise<void> {
    await api.delete(`/offers/${offerId}`, {
        headers: {
            'Authorization': `Bearer ${getToken()}`
        }
    });
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
    