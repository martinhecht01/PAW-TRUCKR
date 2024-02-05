import {afterAll, afterEach, vi} from 'vitest';
import { fireEvent, screen, render} from '@testing-library/react';
import userEvent from "@testing-library/user-event";
import Login from "../../pages/login.tsx";
import {ConfigProvider} from "antd";
// import {useTranslation} from "react-i18next";

const mocks = vi.hoisted(() => {
    return {
        loginUser: vi.fn(),
        useNavigate: vi.fn(),
    }
})

vi.mock('../../api/userApi.tsx', () => {
    return {
        loginUser: mocks.loginUser,
    }
})



 // const {t} = useTranslation()

const customRender = (ui: React.ReactElement, options = {}) => render(ui, {
    wrapper: ({ children }) => <ConfigProvider prefixCls="bingo">{children}</ConfigProvider>,
    ...options,
});

afterAll(() => {
    vi.clearAllMocks();
} );

afterEach(() => {
    localStorage.clear();
})

vi.mock('react-router-dom', () => ({
    useNavigate: mocks.useNavigate,
}));

test('Login page', async () => {

    mocks.loginUser.mockImplementation(()=>{localStorage.setItem('token','token');
        return new Promise((resolve) => {
            setTimeout(() => {
                const authToken = "token";
                resolve(authToken);
            }, 1000);})});


    customRender(<Login/>);

    fireEvent.change(screen.getByTestId('cuit-login'),
    { target: { value: '20-43724688-3' }
    });
    fireEvent.change(screen.getByTestId('password-login'),
    { target: { value: 'password' }
    });

    await userEvent.click(screen.getByTestId('button-login'));

    expect(localStorage.getItem('token')).not.toBeNull();
    expect(mocks.useNavigate).toHaveBeenCalledOnce();
});

test('Login page with invalid credentials', async () => {

    mocks.loginUser.mockRejectedValue(null);

    customRender(<Login/>);

    fireEvent.change(screen.getByTestId('cuit-login'),
    { target: { value: '20-43724688-3' }
    });
    fireEvent.change(screen.getByTestId('password-login'),
    { target: { value: 'password' }
    });

    await userEvent.click(screen.getByTestId('button-login'));

    expect(localStorage.getItem('token')).toBeNull();
});