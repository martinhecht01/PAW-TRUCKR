import React from 'react';
import { useNavigate } from 'react-router-dom';
import useAuth from '../hooks/authState';
import { getClaims } from '../api/userApi';
import AccessDenied403 from '../pages/403';

interface CustomRouteProps {
    render: (props: any) => JSX.Element;
    possibleRoles?: string[];
    noAuth?: boolean;
}

export const CustomRoute: React.FC<CustomRouteProps> = ({ render, possibleRoles, noAuth }) => {
    const { isAuthenticated } = useAuth();
    const claims = getClaims();
    const navigate = useNavigate();

    if (noAuth && !isAuthenticated) {
        return render({});
    }

    if (isAuthenticated && claims) {
        if (possibleRoles?.includes(claims.role)) {
            return render({});
        } else {
            return <AccessDenied403 />;
        }
    }

    if (noAuth && isAuthenticated) {
        navigate(-1);
        return null;
    }

    return <AccessDenied403/>; // Or redirect as needed
};
