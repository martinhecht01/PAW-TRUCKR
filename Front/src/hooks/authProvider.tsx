import React, { createContext, useContext, ReactNode } from 'react';
import useAuth from './authState';

// Define the shape of your authentication context data
interface AuthContextData {
  isAuthenticated: boolean;
  token: string;
  login: (token: string) => void;
  logout: () => void;
}

// Create the context with a default value
const AuthContext = createContext<AuthContextData>({
  isAuthenticated: false,
  token: '',
  login: () => {},
  logout: () => {},
});

interface AuthProviderProps {
  children: ReactNode;
}

// Define the provider component
export const AuthProvider: React.FC<AuthProviderProps> = ({ children }) => {
  const { isAuthenticated, token, login, logout } = useAuth();

  return (
    <AuthContext.Provider value={{ isAuthenticated, token, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

// Hook to use the auth context
export const useAuthContext = () => useContext(AuthContext);

export default AuthProvider;
