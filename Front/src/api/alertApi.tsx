import {Alert} from "../models/Alert.tsx";
import api from "./config";

export async function getAlert(): Promise<Alert> {
    const response = await api.get(`/alerts`);
    return Alert.alertFromJson(response.data);
}

export async function deleteAlert(): Promise<Alert>{
    const response = await api.delete(`/alerts`);
    return Alert.alertFromJson(response.data);
}

export async function createAlert(maxWeight:number, maxVolume:number, fromDate: Date, toDate:Date, origin:String, cargoType:String): Promise<Alert>{
    const response = await api.post(`/alerts?maxWeight=${maxWeight}maxVolume=${maxVolume}fromDate=${fromDate.toDateString()}toDate=${toDate.toDateString()}origin=${origin}cargoType=${cargoType}`);
    return Alert.alertFromJson(response.data);
}
