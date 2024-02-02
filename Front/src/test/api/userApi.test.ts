import '@testing-library/jest-dom';
import {getUserById} from '../../api/userApi.tsx'

test('Get user', async () => {
    const res = await getUserById(1);

    expect(res).toBe(200);
})

// test('Button component renders correctly', () => {
//     render(<Button label="Click me" onClick={() => {}} />);
//
//     // Check if the button is rendered with the correct label
//     const buttonElement = screen.getByText('Click me');
//     expect(buttonElement).toBeInTheDocument();
// });
//
// test('Button click event works', () => {
//     // Mock a function to handle the click event
//     const onClickMock = jest.fn();
//
//     render(<Button label="Click me" onClick={onClickMock} />);
//
//     // Simulate a button click
//     const buttonElement = screen.getByText('Click me');
//     userEvent.click(buttonElement);
//
//     // Check if the onClick function was called
//     expect(onClickMock).toHaveBeenCalled();
// });