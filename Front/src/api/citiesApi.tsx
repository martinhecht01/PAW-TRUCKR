
import { City } from '../models/City';
import api from './config'

export async function getCities(): Promise<City[]> {
    const response = await api.get('/cities')

    const toRet = [];
    for(const city of response.data){
        toRet.push(City.cityFromJson(city));
    }
    
    return toRet;
}