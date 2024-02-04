import {afterAll, afterEach, vi} from 'vitest';
import { fireEvent, screen, render} from '@testing-library/react';
import userEvent from "@testing-library/user-event";
import '@testing-library/jest-dom'
import Login from "../../pages/login.tsx";
import {ConfigProvider} from "antd";


const customRender = (ui: React.ReactElement, options = {}) => render(ui, {
    wrapper: ({ children }) => <ConfigProvider prefixCls="bingo">{children}</ConfigProvider>,
    ...options,
});


afterEach(() => {
    localStorage.clear();
    // mockLogin.mockClear();
})

const mockedUsedNavigate = vi.fn();

// const mockedAuthLogin = vi.spyOn(useAuthContext(), 'login');
// mockedAuthLogin
afterAll(() => {
    vi.clearAllMocks();
} );

afterEach(() => {
    localStorage.clear();
})

vi.mock('react-router-dom', () => ({
    useNavigate: () => mockedUsedNavigate,
}));

vi.mock('../../api/userApi.tsx', () => ({
    loginUser: () => {localStorage.setItem('token','token');return new Promise((resolve) => {
        setTimeout(() => {
            const authToken = "token"; // Your string value
            resolve(authToken);
        }, 1000); // Adjust the timeout as needed
    });}
}));

test('Login page', async () => {

    customRender(<Login/>);

    fireEvent.change(screen.getByTestId('cuit-login'),
    { target: { value: '20-43724688-3' }
    });
    fireEvent.change(screen.getByTestId('password-login'),
    { target: { value: 'password' }
    });

    await userEvent.click(screen.getByTestId('button-login'));

    // expect(screen.getByTestId('cuit-login')).toBeInTheDocument()
    expect(localStorage.getItem('token')).not.toBeNull();
    // expect(mockedUsedNavigate).toHaveBeenCalledWith('/profile');
    //TODO : fix and expand this test
})