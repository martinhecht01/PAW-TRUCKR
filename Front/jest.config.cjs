module.exports = {
    transform: {
        '^.+\\.(ts|tsx)$': 'babel-jest',
    },
    transformIgnorePatterns: ["/node_modules/(?!(axios)/)"],
    resetMocks: false,
    testEnvironment: 'jest-environment-jsdom'
    // Other Jest configurations can be added here
};