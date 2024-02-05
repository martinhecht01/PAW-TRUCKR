import {afterEach, vi} from 'vitest';
import { fireEvent, screen, render} from '@testing-library/react';
import userEvent from "@testing-library/user-event";
import '@testing-library/jest-dom'
import {ConfigProvider} from "antd";
import ResetPasswordRequest from "../../pages/resetPasswordRequest.tsx";
// import ResetPassword from "../../pages/resetPassword.tsx";

const mocks = vi.hoisted(() => {
    return {
        resetPasswordRequest: vi.fn(),
        useLocation: vi.fn(),
        useSearchParams: vi.fn(),
        // searchParamsGet: vi.fn(),
        resetPassword: vi.fn()
    }
})

vi.mock('../../api/userApi.tsx', () => {
    return {
        resetPasswordRequest: mocks.resetPasswordRequest,
        resetPassword: mocks.resetPassword
    }
})

vi.mock('react-router-dom', () => ({
    useLocation: mocks.useLocation,
    useSearchParams: mocks.useSearchParams,
}));

vi.spyOn(URLSearchParams.prototype, 'get').mockReturnValueOnce('00000').mockReturnValueOnce('20-43724688-3').mockReturnValueOnce('1');

// vi.spyOn(mocks.searchParams, 'get');

const customRender = (ui: React.ReactElement, options = {}) => render(ui, {
    wrapper: ({ children }) => <ConfigProvider prefixCls="bingo">{children}</ConfigProvider>,
    ...options,
});

afterEach(() => {
    localStorage.clear();
    vi.clearAllMocks();
})

test('Reset password request', async () => {

    customRender(<ResetPasswordRequest></ResetPasswordRequest>);

        fireEvent.change(screen.getByTestId('cuit-resetPasswordRequest'),
        { target: { value: '20-43724688-3' }
        });

        await userEvent.click(screen.getByTestId('button-resetPasswordRequest'));

        expect(screen.getByTestId('sent-resetPasswordRequest')).toBeInTheDocument();
})

test('Reset password request fail', async () => {

    mocks.resetPasswordRequest.mockRejectedValueOnce(new Error('error'));

    customRender(<ResetPasswordRequest/>);

    fireEvent.change(screen.getByTestId('cuit-resetPasswordRequest'),
    { target: { value: '20-4372468-3' } //Invalid cuit
    });

    await userEvent.click(screen.getByTestId('button-resetPasswordRequest'));

    console.log(screen.debug());

    expect(screen.getByTestId('button-resetPasswordRequest')).toBeInTheDocument();
    expect(mocks.resetPasswordRequest).toHaveBeenCalledTimes(0); //No se llama a la funcion porque el cuit es invalido

});

