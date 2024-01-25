import {Review} from "../models/Review.tsx";
import api from "./config";

const reviewsEndpoint = '/reviews'

export async function createReview(review: Review): Promise<Review> {
    const response = await api.post(reviewsEndpoint, {
        review: review.review,
        rating: review.rating,
        tripId: review.trip
    });
    return Review.reviewFromJson(response.data);
}

export async function getReview(review: Review): Promise<Review> {
    const response = await api.get(`/reviews/${review.user}/${review.trip}`);
    return Review.reviewFromJson(response.data);
}

export async function getReviewsByUser(id: number): Promise<Review[]> {
    const response = await api.get(`${reviewsEndpoint}?userId=${id}`);

    console.log(response.data)

    const toRet = []
    for (const review of response.data) {
        toRet.push(Review.reviewFromJson(review))
    }

    return toRet;
}
