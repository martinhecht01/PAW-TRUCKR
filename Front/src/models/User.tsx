import { Alert } from "./Alert";
import { Offer } from "./Offer";
import { Review } from "./Review";
import { Trip } from "./Trip";

export class User {
    id: number;
    name: string;
    email: string;
    cuit: string;
    password: string;
    rating: number;
    ratingCount: number;
    role: string;
    imageUrl: string;
    offers: Offer[];
    truckerTrips: Trip[];
    providerTrips: Trip[];
    reviews: Review[];
    alert: Alert;
    
   constructor(id: number, name: string, email: string, cuit: string, password: string, rating: number, ratingCount: number, role: string, imageUrl: string, offers: Offer[], truckerTrips: Trip[], providerTrips: Trip[], reviews: Review[], alert: Alert) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.cuit = cuit;
        this.password = password;
        this.rating = rating;
        this.ratingCount = ratingCount;
        this.role = role;
        this.imageUrl = imageUrl;
        this.offers = offers;
        this.truckerTrips = truckerTrips;
        this.providerTrips = providerTrips;
        this.reviews = reviews;
        this.alert = alert;
    }

    static userFromJson(json: any): User {
        return new User(json.id, json.name, json.email, json.cuit, json.password, json.rating, json.ratingCount, json.role, json.imageUrl, json.offers, json.truckerTrips, json.providerTrips, json.reviews, json.alert);
    }

    static userToJson(user: User): any {
        return {
            id: user.id,
            name: user.name,
            email: user.email,
            cuit: user.cuit,
            password: user.password,
            rating: user.rating,
            ratingCount: user.ratingCount,
            role: user.role,
            imageUrl: user.imageUrl,
            offers: user.offers,
            truckerTrips: user.truckerTrips,
            providerTrips: user.providerTrips,
            reviews: user.reviews,
            alert: user.alert
        }
    }

}