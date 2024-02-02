import '@testing-library/jest-dom';
import { mswServer } from './mswServer.js';

export const setupTests = () => {
    beforeAll(() => {
        mswServer.listen()
    });
    afterEach(() => mswServer.resetHandlers());
    afterAll(() => mswServer.close());
}