export default {
    testEnvironment: 'node',
    transform: {
        '^.+\\.tsx?$': 'ts-jest',
    },
    moduleNameMapper: {
        '^@testing-library/jest-dom$': '@testing-library/jest-dom',
    },
    // Add any other Jest configurations you may need
};