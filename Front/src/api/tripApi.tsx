import { Trip } from '../models/Trip';
import api from './config'

{/*
**TRIPS**

Path: “/trips”

- GET individual

/trips/{tripId}

- GET publications

public Response getPublications(
@QueryParam("userId") Integer userId,
@QueryParam("status") @DefaultValue("ongoing") String status,
@QueryParam("page") @DefaultValue(PAGE) int page,
@QueryParam("pageSize") @DefaultValue(PAGE_SIZE) int pageSize,
@QueryParam("origin") String origin,
@QueryParam("destination") String destination,
@QueryParam("minAvailableVolume") Integer minAvailableVolume,
@QueryParam("minAvailableWeight") Integer minAvailableWeight,
@QueryParam("minPrice") Integer minPrice,
@QueryParam("maxPrice") Integer maxPrice,
@QueryParam("sortOrder") String sortOrder,
@QueryParam("departureDate") String departureDate,
@QueryParam("arrivalDate") String arrivalDate,
@QueryParam("type") String type)

https://lh7-us.googleusercontent.com/FbMcWm5wU7Y-DATJrfCa5vWTeV9E8aOKK4RW_zOk9kKlqog0OUPRWaLn-89-MZMUE46NIUX_Tot4-_oFa5CwYMCiLZRggmAARzt60W4euFVwgYFZhMpkSJHKEPQaeT7fMnPSOj2M-M4QZVf30wQIGMo

todos los filtros como queryparam y los cosos de pagination

- POST createTrip

recibe un tripform. (va en el header)

- PUT confirmtrip

Path =“/{id}”
*/}

// MODIFICAR TODO.

const tripsEndpoint = '/trips'

export async function getTrip(id: number): Promise<Trip>{
    const response = await api.get(`${tripsEndpoint}/${id}`)
    return Trip.tripFromJson(response.data)
}

export async function getPublications(userId: number, status: string, page: number, pageSize: number, origin: string, destination: string, minAvailableVolume: number, minAvailableWeight: number, minPrice: number, maxPrice: number, sortOrder: string, departureDate: string, arrivalDate: string, type: string): Promise<Trip[]>{
    const response = await api.get(`${tripsEndpoint}?userId=${userId}&status=${status}&page=${page}&pageSize=${pageSize}&origin=${origin}&destination=${destination}&minAvailableVolume=${minAvailableVolume}&minAvailableWeight=${minAvailableWeight}&minPrice=${minPrice}&maxPrice=${maxPrice}&sortOrder=${sortOrder}&departureDate=${departureDate}&arrivalDate=${arrivalDate}&type=${type}`)
    return response.data.map((tripData: any) => {
        return Trip.tripFromJson(tripData);
    });
}

export async function getTrips(status: String): Promise<Trip[]>{
    try {
        const jwt = sessionStorage.getItem("token");
        const response = await api.get(`${tripsEndpoint}?status=${status}`, {
            headers: {
                'Authorization': `Bearer ${jwt}`
            }
        })
        return response.data.map((tripData: any) => {
            return Trip.tripFromJson(tripData);
        });
    } catch(e){
        return []
    }
}

export async function createTrip(trip: Trip): Promise<Trip>{
    const response = await api.post(tripsEndpoint, Trip.tripToJson(trip))
    return Trip.tripFromJson(response.data)
}


