import {afterEach, vi} from 'vitest';
import {publicationMock} from '../mocks/models.ts'
import api from '../../api/config.tsx'
import {getPublicationById, getPublicationByUrl, getPublications} from "../../api/tripApi";
import {Publication} from "../../models/Publication";


afterEach(() => {
    mockedGet.mockClear();
    mockedPost.mockClear();
    mockedDelete.mockClear();
    localStorage.clear();
})

const mockedGet = vi.spyOn(api,'get');
const mockedDelete = vi.spyOn(api,'delete');
const mockedPost = vi.spyOn(api,'post');


describe('Trip API', () => {
    test('Get publications', async () => {
        // Mock the API response
        mockedGet.mockResolvedValueOnce({ data: [ publicationMock]});

        // Call the function
        const publications = await getPublications("23","TRIP","2024-02-13T22:47:57-03:00","2024-03-11T22:47:57-03:00","",50,780,"Refrigerated","Buenos Aires","Chajari",9000,11000,1,12,""); // Provide necessary parameters (page and limit

        // Assertions
        expect(publications).toEqual([Publication.publicationFromJson(publicationMock)]);

    });
test('Get publication by ID', async () => {
    // Mock the API response
    mockedGet.mockResolvedValue({ data:  publicationMock});


    // Call the function
    const publication = await getPublicationById('95'); // Provide a valid ID

    // Assertions
    expect(publication).toEqual(Publication.publicationFromJson(publicationMock));
    // expect(mockedGet).toHaveBeenCalledWith('/trips/1', {
    //     headers: {
    //         Accept: 'application/vnd.trip.v1+json',
    //     },
    // });
});



    test('Get publication by URL', async () => {
        // Mock the API response
        mockedGet.mockResolvedValue({ data: publicationMock});

        // Call the function
        const trip = await getPublicationByUrl('/trips/95'); // Provide a valid URL

        // Assertions
        expect(trip).toEqual(Publication.publicationFromJson(publicationMock));
        expect(mockedGet).toHaveBeenCalledWith('/trips/95', {
            headers: {
                Accept: 'application/vnd.publication.v1+json',
            },
        });
    });
});



