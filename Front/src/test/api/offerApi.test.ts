// import { mocked } from 'ts-jest/utils';
// import { acceptOffer, createCounterOffer, createOffer, deleteOffer, getOffer, getOfferByUrl, getOffersByTrip, getOffersByUser } from '../../api/offerApi';
// import { Offer } from '../../models/Offer';
// import api from '../../api/config';
// import { getMaxPage } from '../../api/paginationHelper';
// import { getToken } from '../../api/userApi';
// import {offerMock} from "../mocks/models";
//
// jest.mock('../../api/config');
// jest.mock('../../api/paginationHelper');
// jest.mock('../../api/userApi');
//
// const mockedPatch = mocked(api.patch);
// const mockedGet = mocked(api.get);
// const mockedPost = mocked(api.post);
// const mockedDelete = mocked(api.delete);
// const mockedGetToken = mocked(getToken);
//
// describe('Offer API', () => {
//     afterEach(() => {
//         mockedPatch.mockClear();
//         mockedGet.mockClear();
//         mockedPost.mockClear();
//         mockedDelete.mockClear();
//         mockedGetToken.mockClear();
//     });
//
//     test('Accept Offer', async () => {
//         // Mock the API response
//         mockedPatch.mockResolvedValueOnce({ data: { offerMock }, status: 200 });
//
//         // Call the function
//         const offer = await acceptOffer('1', 'ACCEPT'); // Provide a valid offer ID
//
//         // Assertions
//         expect(offer).toEqual(Offer.offerFromJson(offerMock));
//         expect(mockedPatch).toHaveBeenCalledWith('/offers/1', {
//                 action: 'ACCEPT',
//             },
//             {
//                 headers: {
//                     'Authorization': `Bearer ${getToken()}`,
//                     'Content-Type': 'application/vnd.offer.v1+json',
//                 },
//             });
//     });
//
//     test('Get Offers by Trip', async () => {
//         // Mock the API response
//         mockedGet.mockResolvedValue({ data: [{ /* Mocked offer data */ }], headers: { link: 'LinkHeader' }, status: 200 });
//
//         // Mock getMaxPage function
//         mockedGetToken.mockReturnValue('mockedToken');
//         mockedGet.mockReturnValueOnce('LinkHeader');
//
//         // Call the function
//         const offers = await getOffersByTrip('1', '1', '10'); // Provide a valid trip ID, page, and pageSize
//
//         // Assertions
//         expect(offers).toEqual([Offer.offerFromJson(offerMock)]);
//         expect(mockedGet).toHaveBeenCalledWith('/offers?tripId=1&page=1&pageSize=10', {
//             headers: {
//                 'Authorization': `Bearer ${getToken()}`,
//             },
//         });
//         expect(mockedGetToken).toHaveBeenCalled();
//         expect(getMaxPage).toHaveBeenCalledWith('LinkHeader');
//     });
//
//     // Add more test cases for other functions...
//
// });

