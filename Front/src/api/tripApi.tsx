import { Publication } from '../models/Publication';
import { Trip } from '../models/Trip';
import api from './config'
import { getToken } from './userApi';
import { getMaxPage } from './paginationHelper';

export async function getPublications(
    userId: string,
    tripOrRequest: string,
    departureDate: string,
    arrivalDate: string,
    status: string,
    volume: number,
    weight: number,
    cargoType: string,
    origin: string,
    destination: string,
    minPrice: number,
    maxPrice: number,
    page: number,
    pageSize: number,
    sortOrder: string
): Promise<Publication[]> {
    
    const params: any = {
        userId,
        tripOrRequest,
        status,
        volume,
        weight,
        origin,
        destination,
        minPrice,
        maxPrice,
        page,
        pageSize,
        sortOrder
    };

    if (departureDate) params.departureDate = departureDate;
    if (arrivalDate) params.arrivalDate = arrivalDate;
    if (cargoType) params.cargoType = cargoType;

    const response = await api.get('/trips', {
        headers: {
            Accept: 'application/vnd.publicationList.v1+json'
        },
        params: params
    });
    
    const toRet = [];

    for (const pub of response.data) {
        toRet.push(Publication.publicationFromJson(pub));
    }

    if(response.status === 200)
        toRet[0].maxPage = getMaxPage(response.headers['link']).toString();

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

export async function getPublicationByUrl(url: string): Promise<Trip> {

    const response = await api.get(url, {
        headers: {
            Accept: 'application/vnd.publication.v1+json',
        }
    });
    return Trip.tripFromJson(response.data);
}

export async function getTrips(
    status: string,
    page: string,
    pageSize: string
): Promise<Trip[]> {

    const token = localStorage.getItem('token')

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
    
    if(response.status === 200)
        toRet[0].maxPage = getMaxPage(response.headers['link']).toString();

    return toRet;

}

export async function getTripById(id: string): Promise<Trip> {


    const response = await api.get(`/trips/${id}`, {
        headers: {
            Accept: 'application/vnd.trip.v1+json',
            Authorization: `Bearer ${getToken()}`
        }
    });

    return Trip.tripFromJson(response.data);
}

export async function getTripByUrl(url: string): Promise<Trip> {

    const token = localStorage.getItem('token')

    const response = await api.get(url, {
        headers: {
            Accept: 'application/vnd.trip.v1+json',
            Authorization: `Bearer ${token}`
        }
    });
    return Trip.tripFromJson(response.data);
}

    export async function createTrip(
        licensePlate: string,
        availableWeight: number,
        availableVolume: number,
        price: number,
        departureDate: string,
        arrivalDate: string,
        cargoType: string,
        origin: string,
        destination: string,
        imageId: string
    ): Promise<Trip> {

        const response = await api.post('/trips', {
            licensePlate: licensePlate,
            availableWeight: availableWeight,
            availableVolume: availableVolume,
            price: price,
            departureDate: departureDate,
            arrivalDate: arrivalDate,
            cargoType: cargoType,
            origin: origin,
            destination: destination,
            imageId: imageId
        }, {
            headers: {
                Accept: 'application/vnd.trip.v1+json',
                "Content-Type": 'application/vnd.trip.v1+json',
                Authorization: `Bearer ${getToken()}`
            }
        });

        return Trip.tripFromJson(response.data);
    }

export async function confirmTrip(id: string): Promise<void> {

    await api.patch(`/trips/${id}`, {}, {
        headers: {
            Accept: 'application/vnd.trip.v1+json',
            "Content-Type": 'application/vnd.trip.v1+json',
            Authorization: `Bearer ${getToken()}`
        }
    });
}