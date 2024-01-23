import {Review} from "../models/Review.tsx";
import api from "./config";

export async function createReview(review: Review): Promise<Review> {
    const response = await api.post(`/reviews`, Review.reviewToJson(review));
    return Review.reviewFromJson(response.data);
}

// export async function getReview(review: Review): Promise<Review> {
//     const responde = await api.get(`/reviews/${review.user}/${review.trip}`, Review.reviewToJson(review));
//     return Review.reviewFromJson(responde.data);
// }

export async function getReviewsByUser(id: number): Promise<Review[]> {
    const response = await api.get(`/reviews?userId=${id}`);

    return response.data.map((reviewData: any) => {
        return Review.reviewFromJson(reviewData);
    });
}
