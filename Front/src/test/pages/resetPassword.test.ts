import {afterEach, vi} from 'vitest';
import {act, fireEvent, render, screen} from '@testing-library/react';
import userEvent from "@testing-library/user-event";
import '@testing-library/jest-dom'
// import Login from "../../pages/login.tsx";
//
// const customRender = (ui: React.ReactElement, options = {}) => render(ui, {
//     wrapper: ({ children }) => children,
//     ...options,
// });

afterEach(() => {
    localStorage.clear();
})

test('Reset password request', async () => {

        // const {container} = render(<Login/>);
        // const {container} = customRender(<Login/>);

        fireEvent.change(screen.getByTestId('cuit-resetPasswordRequest'),
        { target: { value: '20-4372466-88' }  //TODO: deshardcodear cuit, contra y todo eso. Mockear?
        });

        await userEvent.click(screen.getByTestId('button-resetPasswordRequest'));

        expect(screen.getByTestId('sent-resetPasswordRequest')).toBeInTheDocument();
})

test('Reset password request fail', async () => {

    // const {container} = render(<Login/>);
    // const {container} = customRender(<Login/>);

    fireEvent.change(screen.getByTestId('cuit-resetPasswordRequest'),
    { target: { value: '20-4372466-00' }
    });

    await userEvent.click(screen.getByTestId('button-resetPasswordRequest'));

    expect(screen.getByTestId('sent-resetPasswordRequest')).not.toBeInTheDocument();
});

test('Reset password', async () => { //TODO: hacer que cargue con los query params

    // const {container} = render(<Login/>);
    // const {container} = customRender(<Login/>);

    fireEvent.change(screen.getByTestId('password-resetPassword'),
    { target: { value: 'holahola1' }
    });

    fireEvent.change(screen.getByTestId('repeatPassword-resetPassword'),
    { target: { value: 'holahola1' }
    });

    await userEvent.click(screen.getByTestId('button-resetPassword'));

    expect(screen.getByTestId('success-resetPassword')).toBeInTheDocument();
})

test('Reset password fail', async () => {

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
