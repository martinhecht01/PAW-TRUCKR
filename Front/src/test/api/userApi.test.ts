import {afterEach, vi} from 'vitest';
import {userMock} from '../mocks/models.ts'
import api from '../../api/config.tsx'
import {
    createUser,
    getUserById,
    getUserByUrl,
    updateUser
} from "../../api/userApi.tsx";
import {User} from "../../models/User";


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

describe('User API', () => {
    // Test for createUser function
    test('Create User', async () => {
        // Mock the API response
        mockedPost.mockResolvedValueOnce({ data: userMock, status: 201 });

        // Call the function
        const createdUser = await createUser(User.userFromJson(userMock));

        // Assertions
        expect(createdUser).toEqual(User.userFromJson(userMock));
    });




    // Test for getUserByUrl function
    test('Get User By URL', async () => {
        // Mock the API response
        mockedGet.mockResolvedValueOnce({ data: userMock });

        // Call the function
        const retrievedUser = await getUserByUrl('/users/123');

        // Assertions
        expect(retrievedUser).toEqual(User.userFromJson(userMock));
    });

    // Test for getUserById function
    test('Get User By ID', async () => {
        // Mock the API response
        mockedGet.mockResolvedValueOnce({ data: userMock });

        // Call the function
        const retrievedUser = await getUserById('123');

        // Assertions
        expect(retrievedUser).toEqual(User.userFromJson(userMock));
    });

    // Test for updateUser function
    test('Update User', async () => {
        // Mock the API response
        mockedPatch.mockResolvedValueOnce({ data: userMock });

        // Call the function
        const updatedUser = await updateUser('NewName', 'newImageId', 123);

        // Assertions
        expect(updatedUser).toEqual(User.userFromJson(userMock));
    });

});