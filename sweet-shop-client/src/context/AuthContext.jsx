import { createContext, useContext, useState, useEffect } from 'react';
import { authService } from '../services/authService';

const AuthContext = createContext(undefined);

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const storedUser = localStorage.getItem('user');
    if (storedUser) {
      setUser(JSON.parse(storedUser));
    }
    setLoading(false);
  }, []);

  const login = async (data) => {
    const response = await authService.login(data);
    // Decode JWT to get user info
    const payload = JSON.parse(atob(response.accessToken.split('.')[1]));
    const userData = {
      id: payload.sub, // subject contains user ID
      name: data.email.split('@')[0], // Extract name from email
      email: payload.email,
      role: payload.role,
      createdAt: new Date().toISOString(),
    };
    setUser(userData);
    localStorage.setItem('user', JSON.stringify(userData));
  };

  const register = async (data) => {
    await authService.register(data);
  };

  const logout = () => {
    authService.logout();
    setUser(null);
  };

  const value = {
    user,
    isAuthenticated: !!user,
    isAdmin: user?.role === 'ADMIN',
    login,
    register,
    logout,
    loading,
  };

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (context === undefined) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};
