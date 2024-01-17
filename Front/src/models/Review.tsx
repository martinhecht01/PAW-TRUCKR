export class Review {
    user: number;
    trip: number;
    rating: number;
    review: string;

    constructor(user: number, trip: number, rating: number, review: string){
        this.user = user;
        this.trip = trip;
        this.rating = rating;
        this.review = review;
    }

    static reviewFromJson(json: any): Review {
        return new Review(json.user, json.trip, json.rating, json.review);
    }

    static reviewToJson(review: Review): any {
        return {
            user: review.user,
            trip: review.trip,
            rating: review.rating,
            review: review.review
        }
    }
}