// import {afterEach, vi} from 'vitest';TODO: no se si esta bien
// import {createAlert, deleteAlert, getAlert} from "../../api/alertApi.tsx";
// import {Alert} from "../../models/Alert.tsx";
// import {alertMock} from '../mocks/models.ts'
// import {userMock} from '../mocks/models.ts'
// import {tripMock} from '../mocks/models.ts'
// import api from '../../api/config.tsx'
// import {getToken} from "../../api/userApi.tsx";
// import {getPublicationById, getPublicationByUrl, getPublications} from "../../api/tripApi";
// import {Publication} from "../../models/Publication";
//
//
// afterEach(() => {
//     mockedGet.mockClear();
//     mockedPost.mockClear();
//     mockedDelete.mockClear();
//     localStorage.clear();
// })
//
// const mockedGet = vi.spyOn(api,'get');
// const mockedDelete = vi.spyOn(api,'delete');
// const mockedPost = vi.spyOn(api,'post');
// ;
//
// describe('Publication API', () => {
//     test('Get publications', async () => {
//         // Mock the API response
//         mockedGet.mockResolvedValue({ data: [{ tripMock }], status: 200, headers: { link: '<https://api.example.com/publications?page=2>; rel="next"' } });
//
//         // Call the function
//         const publications = await getPublications(); // Provide necessary parameters (page and limit
//
//         // Assertions
//         expect(publications).toEqual([Publication.publicationFromJson(tripMock)]);
//         expect(publications[0].maxPage).toBe('2');
//         expect(mockedGet).toHaveBeenCalledWith('/trips', {
//             headers: {
//                 Accept: 'application/vnd.publicationList.v1+json',
//             },
//             params: {/* provide necessary parameters */ },
//         });
//     });
//
//     test('Get publication by ID', async () => {
//         // Mock the API response
//         mockedGet.mockResolvedValue({ data: { tripMock}, status: 200 });
//
//         // Call the function
//         const publication = await getPublicationById('1'); // Provide a valid ID
//
//         // Assertions
//         expect(publication).toEqual(Publication.publicationFromJson(tripMock));
//         expect(mockedGet).toHaveBeenCalledWith('/trips/1', {
//             headers: {
//                 Accept: 'application/vnd.publication.v1+json',
//             },
//         });
//     });
//
//     test('Get publication by URL', async () => {
//         // Mock the API response
//         mockedGet.mockResolvedValue({ data: {tripMock }, status: 200 });
//
//         // Call the function
//         const trip = await getPublicationByUrl('/trips/1'); // Provide a valid URL
//
//         // Assertions
//         expect(trip).toEqual(Publication.publicationFromJson(tripMock));
//         expect(mockedGet).toHaveBeenCalledWith('/trips/1', {
//             headers: {
//                 Accept: 'application/vnd.publication.v1+json',
//             },
//         });
//     });
// });



