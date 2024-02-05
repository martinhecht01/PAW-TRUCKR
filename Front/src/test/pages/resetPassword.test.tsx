import {afterAll, afterEach, beforeEach, vi} from 'vitest';
import { fireEvent, screen, render} from '@testing-library/react';
import userEvent from "@testing-library/user-event";
import '@testing-library/jest-dom'
import {ConfigProvider} from "antd";
import ResetPasswordRequest from "../../pages/resetPasswordRequest.tsx";
import ResetPassword from "../../pages/resetPassword.tsx";

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
        { target: { value: '20-43724688-3' }  //TODO: deshardcodear cuit, contra y todo eso. Mockear?
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

test('Reset password', async () => {

    mocks.useSearchParams.mockReturnValueOnce({
        toString: () => toString,
    });
    // mocks.useSearchParams.mockReturnValueOnce([new URLSearchParams('?hash=00000&cuit=20-43724688-3&userid=1')]);

    customRender(<ResetPassword/>);

    fireEvent.change(screen.getByTestId('password-resetPassword'),
    { target: { value: 'holahola1' }
    });

    fireEvent.change(screen.getByTestId('repeatPassword-resetPassword'),
    { target: { value: 'holahola1' }
    });

    await userEvent.click(screen.getByTestId('button-resetPassword'));

    console.log(screen.debug())

    // expect(screen.getByTestId('success-resetPassword')).toBeInTheDocument();
})

//TODO: arreglar estos 2 tests

test('Reset password fail different password', async () => {

    // const {container} = render(<Login/>);
    // const {container} = customRender(<Login/>);

    fireEvent.change(screen.getByTestId('password-resetPassword'),
    { target: { value: 'holahola1' }
    });

    fireEvent.change(screen.getByTestId('repeatPassword-resetPassword'),
    { target: { value: 'holahola2' }
    });

    await userEvent.click(screen.getByTestId('button-resetPassword'));

    expect(screen.getByTestId('success-resetPassword')).not.toBeInTheDocument();
});
