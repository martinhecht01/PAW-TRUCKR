import {afterEach, vi} from 'vitest';
import {createAlert, deleteAlert, getAlert} from "../../api/alertApi.tsx";
import {Alert} from "../../models/Alert.tsx";
import {alertMock} from '../mocks/models.ts'
import api from '../../api/config.tsx'
import {getToken} from "../../api/userApi.tsx";

afterEach(() => {
    mockedGet.mockClear();
    mockedPost.mockClear();
    mockedDelete.mockClear();
    localStorage.clear();
})

const mockedGet = vi.spyOn(api,'get');
const mockedDelete = vi.spyOn(api,'delete');
const mockedPost = vi.spyOn(api,'post');


test('Get alert', async () => {
    mockedGet.mockResolvedValue({data:alertMock});

    const res = await getAlert();

    expect(res).toEqual(Alert.alertFromJson(alertMock));
})

test('Get alert fail', async () => {
    mockedGet.mockRejectedValue(new Error('error'));

    try{
        await getAlert();
    }
    catch(e){
        expect(e).toEqual(new Error('error'));
    }
});

test('Delete alert', async () => {
    mockedDelete.mockResolvedValueOnce({data:alertMock,status:200});
    localStorage.setItem('token', 'null');

    const res = await deleteAlert(alertMock.id);

    expect(res).toEqual(Alert.alertFromJson(alertMock));
    expect(mockedDelete).toHaveBeenCalledTimes(1);
    expect(mockedDelete).toHaveBeenCalledWith(`/alerts/${alertMock.id}`, {
        headers:{
            'authorization': `Bearer ${getToken()}`
        }
    })
})

test('Delete alert fail', async () => {
    mockedDelete.mockRejectedValue(new Error('error'));
    localStorage.setItem('token', 'null')

    try{
        await deleteAlert(alertMock.id);
    }
    catch(e){
        expect(e).toEqual(new Error('error'));
    }

    expect(mockedDelete).toHaveBeenCalledTimes(1);
    expect(mockedDelete).toHaveBeenCalledWith(`/alerts/${alertMock.id}`, {
        headers:{
            'authorization': `Bearer ${getToken()}`
        }
    })


});

test('Create alert', async () => {
    mockedPost.mockResolvedValueOnce({data:alertMock,status:201});
    localStorage.setItem('token', 'null')

    const res = await createAlert(alertMock.maxWeight, alertMock.maxWeight,alertMock.fromDate, alertMock.toDate, alertMock.city, alertMock.cargoType);

    expect(res).toEqual(Alert.alertFromJson(alertMock));
    expect(mockedPost).toHaveBeenCalledTimes(1);
    expect(mockedPost).toHaveBeenCalledWith('/alerts',
        {
            "maxWeight":alertMock.maxWeight,
            "maxVolume":alertMock.maxVolume,
            "fromDate":alertMock.fromDate,
            "toDate":alertMock.toDate,
            "cargoType":alertMock.cargoType,
            "origin":alertMock.city
        },
        {
            headers:{
                'Content-Type': 'application/vnd.alert.v1+json',
                'Accept':'application/vnd.alert.v1+json',
                'Authorization': `Bearer ${getToken()}`
            }
        })
})

test('Create alert fail', async () => {
    mockedPost.mockRejectedValue(new Error('error'));
    localStorage.setItem('token', 'null')

    try{
        await createAlert(alertMock.maxWeight, alertMock.maxWeight,alertMock.fromDate, alertMock.toDate, alertMock.city, alertMock.cargoType);
    }
    catch(e){
        expect(e).toEqual(new Error('error'));
    }

    expect(mockedPost).toHaveBeenCalledTimes(1);
    expect(mockedPost).toHaveBeenCalledWith('/alerts',
        {
            "maxWeight":alertMock.maxWeight,
            "maxVolume":alertMock.maxVolume,
            "fromDate":alertMock.fromDate,
            "toDate":alertMock.toDate,
            "cargoType":alertMock.cargoType,
            "origin":alertMock.city
        },
        {
            headers:{
                'Content-Type': 'application/vnd.alert.v1+json',
                'Accept':'application/vnd.alert.v1+json',
                'Authorization': `Bearer ${getToken()}`
            }
        })
});