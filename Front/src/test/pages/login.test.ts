import {afterEach, vi} from 'vitest';
import {act, fireEvent, render, screen} from '@testing-library/react';
import userEvent from "@testing-library/user-event";
import '@testing-library/jest-dom'
import {loginUser} from "../../api/userApi.tsx";
// import Login from "../../pages/login.tsx";
//
// const customRender = (ui: React.ReactElement, options = {}) => render(ui, {
//     wrapper: ({ children }) => children,
//     ...options,
// });

afterEach(() => {
    localStorage.clear();
    mockLogin.mockClear();
})

const mockLogin = vi.spyOn();

test('Login page', async () => {

    // const {container} = render(<Login/>);
    // const {container} = customRender(<Login/>);

    // mockLogin.mo

    fireEvent.change(screen.getByTestId('cuit-login'),
    { target: { value: '20-4372466-88' }
    });

    fireEvent.change(screen.getByTestId('password-login'),
    { target: { value: 'holahola' }
    });

    await userEvent.click(screen.getByTestId('button-login'));

    expect(localStorage.getItem('token')).not.toBeNull();
})