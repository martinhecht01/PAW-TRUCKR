import axios from "axios";
import {getAlert} from "../../api/alertApi.tsx";
import {alert} from "../modelMocks";
import {Alert} from "../../models/Alert.tsx";


jest.mock("axios");

afterEach(() => {(axios.get as any).mockClear();});
// afterEach(() => {(axios.post as any).mockClear();});
// afterEach(() => {(axios.put as any).mockClear();});

const axiosGet = (axios.get as any);
// const axiosPost = (axios.post as any);
// const axiosPut = (axios.put as any);

test('Get alert', async () => {

    axiosGet.mockResolvedValueOnce({data: alert});

    await getAlert().then( (response) => {
        expect(response).toBe(Alert.alertFromJson(alert));
    });
})