import {afterEach, vi} from 'vitest';
import {createAlert, deleteAlert, getAlert} from "../../api/alertApi.tsx";

import {reviewMock} from '../mocks/models.ts'

import api from '../../api/config.tsx'
import {getToken} from "../../api/userApi.tsx";
import {getPublicationById, getPublicationByUrl, getPublications} from "../../api/tripApi";
import {Publication} from "../../models/Publication";
import {Trip} from "../../models/Trip";
import {Review} from "../../models/Review";
import {createReview, getReview, getReviewsByURL, getReviewsByUser} from "../../api/reviewApi";


afterEach(() => {
    mockedGet.mockClear();
    mockedPost.mockClear();
    mockedDelete.mockClear();
    localStorage.clear();
})

const mockedGet = vi.spyOn(api,'get');
const mockedDelete = vi.spyOn(api,'delete');
const mockedPost = vi.spyOn(api,'post');


describe('Review API', () => {
    // Test for createReview function
    test('Create Review', async () => {
        // Mock the API response
        mockedPost.mockResolvedValueOnce({data: reviewMock});

        // Call the function
        const createdReview = await createReview(Review.reviewFromJson(reviewMock));

        // Assertions
        expect(createdReview).toEqual(Review.reviewFromJson(reviewMock));

    });

    // Test for getReview function
    test('Get Review', async () => {
        // Mock the API response
        mockedGet.mockResolvedValueOnce({data: reviewMock, status: 200});

        // Call the function
        const retrievedReview = await getReview(Review.reviewFromJson(reviewMock));

        // Assertions
        expect(retrievedReview).toEqual(Review.reviewFromJson(reviewMock));
    });

    // Test for getReviewsByUser function
    test('Get Reviews By User', async () => {
        // Mock the API response
        mockedGet.mockResolvedValueOnce({
            data: [reviewMock],
            headers: { link: 'LinkHeader' },
            status: 200,
        });

        // Call the function
        const reviews = await getReviewsByUser(123, '1', '10');

        // Assertions
        expect(reviews).toEqual([Review.reviewFromJson(reviewMock)]);

    });



});