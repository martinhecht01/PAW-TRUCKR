export class Review {
    user: number;
    trip: string;
    rating: number;
    review: string;
    maxPage: string;

    constructor(user: number, trip: string, rating: number, review: string, maxPage: string){
        this.user = user;
        this.trip = trip;
        this.rating = rating;
        this.review = review;
        this.maxPage = maxPage;
    }

    static reviewFromJson(json: any): Review {
        return new Review(json.user, json.trip, json.rating, json.review, json.maxPage);
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