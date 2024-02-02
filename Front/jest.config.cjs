module.exports = {
    transform: {
        '^.+\\.(ts|tsx)$': 'ts-jest',
    },
    transformIgnorePatterns: ["/node_modules/(?!(axios)/)"],
    setupFiles: ["jest-localstorage-mock"]
    // Other Jest configurations can be added here
};