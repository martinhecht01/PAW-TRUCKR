import {afterAll, afterEach, vi} from 'vitest';
import { fireEvent, screen, render} from '@testing-library/react';
import userEvent from "@testing-library/user-event";
import '@testing-library/jest-dom'
import Login from "../../pages/login.tsx";
import {ConfigProvider} from "antd";
import {useTranslation} from "react-i18next";

// const {t} = useTranslation()

const customRender = (ui: React.ReactElement, options = {}) => render(ui, {
    wrapper: ({ children }) => <ConfigProvider prefixCls="bingo">{children}</ConfigProvider>,
    ...options,
});

afterEach(() => {
    localStorage.clear();
    // mockLogin.mockClear();
})

const mockedUseNavigate = vi.fn();

// const mockedAuthLogin = vi.spyOn(useAuthContext(), 'login');
// mockedAuthLogin
afterAll(() => {
    vi.clearAllMocks();
} );

afterEach(() => {
    localStorage.clear();
})

vi.mock('react-router-dom', () => ({
    useNavigate: () => mockedUseNavigate,
}));

test('Login page', async () => {

    vi.mock('../../api/userApi.tsx', () => ({
        loginUser: () => {localStorage.setItem('token','token');return new Promise((resolve) => {
            setTimeout(() => {
                const authToken = "token";
                resolve(authToken);
            }, 1000);
        });}
    }));

    customRender(<Login/>);

    fireEvent.change(screen.getByTestId('cuit-login'),
    { target: { value: '20-43724688-3' }
    });
    fireEvent.change(screen.getByTestId('password-login'),
    { target: { value: 'password' }
    });

    await userEvent.click(screen.getByTestId('button-login'));

    expect(localStorage.getItem('token')).not.toBeNull();
    // expect(mockedUseNavigate).toHaveBeenCalledWith('/profile'); //TODO: ver si puedo hacer esto
});

test('Login page with invalid credentials', async () => {

    vi.mock('../../api/userApi.tsx', () => ({
        loginUser: () => {return new Promise((resolve) => {resolve(null)})}}));

    customRender(<Login/>);

    fireEvent.change(screen.getByTestId('cuit-login'),
    { target: { value: '20-43724688-3' }
    });
    fireEvent.change(screen.getByTestId('password-login'),
    { target: { value: 'password' }
    });

    await userEvent.click(screen.getByTestId('button-login'));

    expect(localStorage.getItem('token')).toBeNull();
    // expect(screen.getByText(t())).toBeInTheDocument(); //TODO : add what needs to be read
});