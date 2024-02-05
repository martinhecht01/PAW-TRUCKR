// import React from "react";
import { afterEach, test, vi} from 'vitest';
// import {  render} from '@testing-library/react';
// import userEvent from "@testing-library/user-event";
import '@testing-library/jest-dom'
// import {ConfigProvider} from "antd";
// import BrowseTrips from "../../pages/browse.tsx";
// import {publicationMock} from "../mocks/models.ts";
// import {Publication} from "../../models/Publication.tsx";
// import {City} from "../../models/City.tsx";


const mocks = vi.hoisted(() => {
    return {
        getPublications : vi.fn(),
        getCities: vi.fn(),
        getCargoTypes: vi.fn(),
    }
})

vi.mock('../../api/tripApi.tsx', () => {
    return {
        getPublications : mocks.getPublications
    }
})

vi.mock('../../api/citiesApi.tsx', () => {
    return {
        getCities : mocks.getCities
    }
})

vi.mock('../../api/cargoTypeApi.tsx', () => {
    return {
        getCargoTypes : mocks.getCargoTypes
    }
});

// vi.mock('react-router-dom', () => ({
//     useLocation: mocks.useLocation,
//     useSearchParams: mocks.useSearchParams,
// }));


// const customRender = (ui: React.ReactElement, options = {}) => render(ui, {
//     wrapper: ({ children }) => <ConfigProvider prefixCls="bingo">{children}</ConfigProvider>,
//     ...options,
// });

afterEach(() => {
    localStorage.clear();
    vi.clearAllMocks();
})


test('Browse trips', async () => {

    // mocks.getPublications.mockResolvedValue([Publication.publicationFromJson(publicationMock)]);
    // mocks.getCities.mockResolvedValue([new City(1,'City1'), new City(2,'City2')]);
    // mocks.getCargoTypes.mockResolvedValue(['Cargo1', 'Cargo2']);
    //
    // vi.spyOn(React, 'useState').mockReturnValue([[Publication.publicationFromJson(publicationMock)] , () => {}]);
    //
    // // vi.spyOn()
    //
    // customRender(<BrowseTrips tripOrRequest={"TRIP"}/>);
    //
    // console.log(screen.debug())
    //
    // expect(screen.getByTestId('tripCard-browse')).toBeInTheDocument();
    // expect(screen.getAllByTestId('tripCard-browse')).toHaveLength(1);
    // expect(screen.getByTestId('pagination-browse')).toBeInTheDocument();
    // expect(screen.getByTestId('browse-trips')).toBeInTheDocument();
})