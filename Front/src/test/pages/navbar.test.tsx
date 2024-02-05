import {afterEach, vi} from 'vitest';
import {screen, render} from '@testing-library/react';
import {ConfigProvider} from "antd";
import Navbar from "../../Components/navbar.tsx";
import React from "react";

const customRender = (ui: React.ReactElement, options = {}) => render(ui, {
    wrapper: ({ children }) => <ConfigProvider prefixCls="bingo">{children}</ConfigProvider>,
    ...options,
});

afterEach(() => {
    vi.clearAllMocks();
} );

const mocks = vi.hoisted(() => {
    return {
        useAuthContext: vi.fn(),
    }
})
vi.mock('../../hooks/authProvider', () => ({
    useAuthContext: mocks.useAuthContext,
}));

vi.mock('react-router-dom', () => ({
    useNavigate: () => {},
}));

test('Navbar logged out', async () => {

    mocks.useAuthContext.mockImplementation(() => {
        return {
            isAuthenticated: false,
        }
    });

    customRender(<Navbar/>);

    expect(screen.getAllByTestId('noAuth-navBar')).toHaveLength(2);

});