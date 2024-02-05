export function getCargoTypeColor(cargoType: string | undefined) {
    switch (cargoType) {
        case 'hazardous':
            return 'red';
        case 'refrigerated':
            return 'blue';
        default:
            return 'green';
    }
}