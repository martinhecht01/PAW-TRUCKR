// // import axios from "axios";
// import {getAlert} from "../../api/alertApi.tsx";
// import {alert} from "../modelMocks";
// import {Alert} from "../../models/Alert.tsx";
// import 'jest-localstorage-mock';
//
// jest.mock('axios', () => {
//     return {
//         create: jest.fn(() => ({
//             get: jest.fn(),
//             interceptors: {
//                 request: { use: jest.fn(), eject: jest.fn() },
//                 response: { use: jest.fn(), eject: jest.fn() }
//             }
//         }))
//     }
// })
//
// import axios from "axios";
// afterEach(() => {(axios.get as any).mockClear();});
// // afterEach(() => {(axios.post as any).mockClear();});
// // afterEach(() => {(axios.put as any).mockClear();});
//
// const axiosGet = (axios.get as any);
// // const axiosPost = (axios.post as any);
// // const axiosPut = (axios.put as any);
//
// // const localStorageMock = (() => {
// //     let store: { [key: string]: string } = {}; // Specify the type of store as { [key: string]: string }
// //
// //     return {
// //         getItem(key: string): string | null {
// //             return store[key] || null;
// //         },
// //         setItem(key: string, value: string): void {
// //             store[key] = value.toString();
// //         },
// //         removeItem(key: string): void {
// //             delete store[key];
// //         },
// //         clear(): void {
// //             store = {};
// //         }
// //     };
// // })();
//
//
// // Object.defineProperty(window, 'sessionStorage', {
// //     value: localStorageMock
// // });
//
import {getAlert} from "../../api/alertApi.tsx";
import {Alert} from "../../models/Alert.tsx";
import {setupTests} from "../mocks/setupTests.js"

setupTests();

window.sessionStorage.setItem('token','hola')
test('Get alert', async () => {

    const res = getAlert();

    expect(res).toEqual(Alert.alertFromJson(alert));

})