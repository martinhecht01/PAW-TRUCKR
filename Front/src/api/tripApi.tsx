import { Publication } from '../models/Publication';
import { Trip } from '../models/Trip';
import api from './config'

export async function getPublications(
    userId: string,
    tripOrRequest: string,
    status: string,
    volume: number,
    weight: number,
    origin: string,
    destination: string,
    minPrice: number,
    maxPrice: number,
    page: number,
    pageSize: number,
    sortOrder: string
): Promise<Publication[]> {
    const response = await api.get('/trips', {
        headers: {
            Accept: 'application/vnd.publicationList.v1+json'
        },
        params: {
            userId: userId,
            tripOrRequest: tripOrRequest,
            status: status,
            volume: volume,
            weight: weight,
            origin: origin,
            destination: destination,
            minPrice: minPrice,
            maxPrice: maxPrice,
            page: page,
            pageSize: pageSize,
            sortOrder: sortOrder
        }
    });
    
    const toRet = [];

    for (const pub of response.data) {
        toRet.push(Publication.publicationFromJson(pub));
    }

    return toRet;

}

export async function getPublicationById(id: string): Promise<Publication> {
    const response = await api.get(`/trips/${id}`, {
        headers: {
            Accept: 'application/vnd.publication.v1+json'
        }
    });
    return Publication.publicationFromJson(response.data);
}

/*
### Get trips. :devuelve los trips del usuario autenticado

- ENDPOINT: GET http://localhost:8080/webapp_war/trips
- Basic or JWT auth required
- Query params:
    - status: ONGOING, PAST o  FUTURE (ONGOING default)
    - page: page (1 default)
    - pageSize: pageSize (12 default)
- Headers:
    - Accept: application/vnd.tripList.v1+json
*/

export async function getTrips(
    status: string,
    page: string,
    pageSize: string
): Promise<Trip[]> {

    const token = sessionStorage.getItem('token')

    const response = await api.get('/trips', {
        headers: {
            Accept: 'application/vnd.tripList.v1+json',
            Authorization: `Bearer ${token}`
        },
        params: {
            status: status,
            page: page,
            pageSize: pageSize
        }
    });
    
    const toRet = [];

    for (const trip of response.data) {
        toRet.push(Trip.tripFromJson(trip));
    }

    return toRet;

}

export async function getTripById(id: string): Promise<Trip> {

    const token = sessionStorage.getItem('token')

    const response = await api.get(`/trips/${id}`, {
        headers: {
            Accept: 'application/vnd.trip.v1+json',
            Authorization: `Bearer ${token}`
        }
    });
    return Trip.tripFromJson(response.data);
}
//- Accept: application/vnd.trip.v1+json

export async function createTrip(trip: Trip): Promise<Trip> {

    const token = sessionStorage.getItem('token')

    const response = await api.post('/trips', {
        licensePlate: trip.licensePlate,
        availableWeight: trip.weight,
        availableVolume: trip.volume,
        price: trip.price,
        departureDate: trip.departureDate,
        arrivalDate: trip.arrivalDate,
        cargoType: trip.type,
        origin: trip.origin,
        destination: trip.destination,
        imageId: trip.image
    }, {
        headers: {
            Accept: 'application/vnd.trip.v1+json',
            "Content-Type": 'application/vnd.trip.v1+json',
            Authorization: `Bearer ${token}`
        }
    });
    return Trip.tripFromJson(response.data);
}

export async function confirmTrip(id: string): Promise<void> {

    const token = sessionStorage.getItem('token')

    await api.patch(`/trips/${id}`, {}, {
        headers: {
            Accept: 'application/vnd.trip.v1+json',
            "Content-Type": 'application/vnd.trip.v1+json',
            Authorization: `Bearer ${token}`
        }
    });
}