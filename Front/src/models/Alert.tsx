export class Alert {
    id: number;
    user_id: number;
    city: string;
    maxWeight: number;
    maxVolume: number;
    fromDate: Date;
    toDate: Date;
    cargoType: string;
    constructor(id: number, user_id: number, city: string, maxWeight: number, maxVolume: number, fromDate: Date, toDate: Date, cargoType: string) {
        this.id = id;
        this.user_id = user_id;
        this.city = city;
        this.maxWeight = maxWeight;
        this.maxVolume = maxVolume;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.cargoType = cargoType;
    }
    static alertFromJson(json: any): Alert {
        return new Alert(json.id, json.user_id, json.city, json.maxWeight, json.maxVolume, json.fromDate, json.toDate, json.cargoType);
    }
    static alertToJson(alert: Alert): any {
        return {
            id: alert.id,
            user_id: alert.user_id,
            city: alert.city,
            maxWeight: alert.maxWeight,
            maxVolume: alert.maxVolume,
            fromDate: alert.fromDate,
            toDate: alert.toDate,
            cargoType: alert.cargoType
        }
    }
}