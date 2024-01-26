export class City {
    cityId: number;
    cityName: string;

    constructor(cityId: number, cityName: string) {
        this.cityId = cityId;
        this.cityName = cityName;
    }

    static cityFromJson(json: any): City {
        return new City(json.cityId, json.cityName);
    }


}