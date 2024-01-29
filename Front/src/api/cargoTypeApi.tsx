import api from './config'

const cargoTypeEndpoint = '/cargoTypes'

export async function getCargoTypes(): Promise<string[]> {
    const response = await api.get(cargoTypeEndpoint, {
        headers: {
            Accept: 'application/vnd.cargoTypeList.v1+json'
        }
    });
    
    const toRet = [];

    for (const cargoType of response.data) {
        toRet.push(cargoType.cargoTypeName);
    }

    return toRet;
}