import {Review} from "../models/Review.tsx";
import api from "./config";
import { getMaxPage } from "./paginationHelper.tsx";
import { getToken } from "./userApi.tsx";

const reviewsEndpoint = '/reviews'

export async function createReview(review: Review): Promise<Review> {
    const response = await api.post(reviewsEndpoint, {
        review: review.review,
        rating: review.rating,
        tripId: review.trip
    }, {
        headers: {
            'Authorization': `Bearer ${getToken()}`,
            'Content-Type': 'application/vnd.review.v1+json'
        }
    });
    return Review.reviewFromJson(response.data);
}

export async function getReview(review: Review): Promise<Review> {
    const response = await api.get(`/reviews/${review.user}/${review.trip}`);
    return Review.reviewFromJson(response.data);
}

export async function getReviewsByUser(id: number, page: string, pageSize: string): Promise<Review[]> {
    const response = await api.get(`${reviewsEndpoint}?userId=${id}&page=${page}&pageSize=${pageSize}`);

    console.log(response.data)

    const toRet = []

    for (const review of response.data) {
        toRet.push(Review.reviewFromJson(review))
    }

    if(response.status === 200)
        toRet[0].maxPage = getMaxPage(response.headers['link']).toString();

    return toRet;
}

export async function getReviewsByURL(url: string): Promise<Review[]> {
    const response = await api.get(url, {
        headers: {
            'Authorization': `Bearer ${getToken()}`
        }
    });

    console.log(response.data)

    const toRet = []
    for (const review of response.data) {
        toRet.push(Review.reviewFromJson(JSON.parse(review)))
    }

    if(response.status === 200)
        toRet[0].maxPage = getMaxPage(response.headers['link']).toString();

    return toRet;
}
