import {Alert} from "../models/Alert.tsx";
import api from "./config";
import {getToken} from "./userApi.tsx";

export async function getAlert(): Promise<Alert> {
    const response = await api.get(`/alerts`,{
        headers:{
            accept: 'application/vnd.alert.v1+json',
            authorization: `Bearer ${getToken()}`
        }
    });
    return Alert.alertFromJson(response.data);
}

export async function deleteAlert(): Promise<Alert>{
    const response = await api.delete(`/alerts`);
    return Alert.alertFromJson(response.data);
}

export async function createAlert(maxWeight:number | undefined, maxVolume:number | undefined, fromDate: string, toDate:string | undefined, origin:String, cargoType:String | undefined): Promise<Alert>{
    const response = await api.post(`/alerts`,{
        "maxWeight":maxWeight,
        "maxVolume":maxVolume,
        "fromDate":fromDate,
        "toDate":toDate,
        "cargoType":cargoType,
        "origin":origin
    }, {
        headers:{
            'Content-Type': 'application/vnd.alert.v1+json',
            'Accept':'application/vnd.alert.v1+json',
            'Authorization': `Bearer ${getToken()}`
        }
    });
    if (response.status != 201){
        throw new Error(response.statusText);
    }
    return Alert.alertFromJson(response.data);
}
