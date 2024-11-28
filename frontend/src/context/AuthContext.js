import React, { createContext, useState } from 'react';

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [isAuthenticated, setIsAuthenticated] = useState(false); // 로그인 여부 상태
  const [userInfo, setUserInfo] = useState({ name: '', id: '', level: '', role: '' }); // 유저 정보 상태

   // 로그인 시 호출, 유저 정보 및 토큰 설정
   const login = ({ token, user }) => {
    setIsAuthenticated(true);
    setUserInfo(user);

    // 로컬 스토리지에도 토큰과 유저 정보 저장
    localStorage.setItem('jwtToken', token);
    localStorage.setItem('user', JSON.stringify(user));
  };

  // 로그아웃 시 호출, 유저 정보 초기화
  const logout = () => {
    setIsAuthenticated(false);
    setUserInfo({ name: '', id: '', level: '', role: '' });

    // 로컬 스토리지에서 토큰과 유저 정보 삭제
    localStorage.removeItem('token');
    localStorage.removeItem('user');
  };

  return (
    <AuthContext.Provider value={{ isAuthenticated, userInfo, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};