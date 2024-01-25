import {Offer} from "../models/Offer.tsx";
import api from "./config";

// export async function createOffer(offer: Offer): Promise<Offer> {
//     const response = await api.post(`/reviews`, Review.reviewToJson(review));
//     return Review.reviewFromJson(response.data);
// }
//
// export async function getReview(review: Review): Promise<Review> {
//     const responde = await api.get(`/reviews/${review.user}/${review.trip}`, Review.reviewToJson(review));
//     return Review.reviewFromJson(responde.data);
// }

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

export async function getOffer(offerId : number) : Promise<Offer> {
    const response = await api.get(`/offers/${offerId}`);
    return Offer.offerFromJson(response.data);
}

export async function createOffer(offer: Offer): Promise<Offer> {
    const response = await api.post(`/offers?tripId=${offer.trip_id}`, {
        price: offer.price,
        description: offer.description
    });
    return Offer.offerFromJson(response.data);
}