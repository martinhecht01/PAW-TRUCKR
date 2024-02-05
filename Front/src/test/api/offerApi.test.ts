import {afterEach, vi} from 'vitest';
import {createAlert, deleteAlert, getAlert} from "../../api/alertApi.tsx";
import {Alert} from "../../models/Alert.tsx";
import {alertMock, offerMock} from '../mocks/models.ts'
import api from '../../api/config.tsx'
import {getToken} from "../../api/userApi.tsx";
import {acceptOffer, getOffersByTrip} from "../../api/offerApi";
import {Offer} from "../../models/Offer";

afterEach(() => {
    mockedGet.mockClear();
    mockedPost.mockClear();
    mockedDelete.mockClear();
    mockedPatch.mockClear();
    localStorage.clear();
})

const mockedGet = vi.spyOn(api,'get');
const mockedDelete = vi.spyOn(api,'delete');
const mockedPost = vi.spyOn(api,'post');
const mockedPatch = vi.spyOn(api,'patch');

describe('Offer API', () => {

    test('Accept Offer', async () => {
        // Mock the API response
        mockedPatch.mockResolvedValueOnce({ data:  offerMock , status: 200 });

        // Call the function
        const offer = await acceptOffer('12', 'ACCEPT'); // Provide a valid offer ID

        // Assertions
        expect(offer).toEqual(Offer.offerFromJson(offerMock));
        expect(mockedPatch).toHaveBeenCalledWith('/offers/12', {
                action: 'ACCEPT',
            },
            {
                headers: {
                    'Authorization': `Bearer ${getToken()}`,
                    'Content-Type': 'application/vnd.offer.v1+json',
                },
            });
    });

    test('Get Offers by Trip', async () => {
        mockedGet.mockResolvedValue({ data: [offerMock], headers: { link: 'LinkHeader' }, status: 200 });
        // Mock getMaxPage function

        // Call the function
        const offers = await getOffersByTrip('4', '1', '10'); // Provide a valid trip ID, page, and pageSize

        // Assertions
        expect(offers).toEqual([Offer.offerFromJson(offerMock)]);

    });



});

