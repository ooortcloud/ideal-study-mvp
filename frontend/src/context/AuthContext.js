import React, { createContext, useEffect, useState } from "react";
import { parseJwtToken } from "../utils/auth/parseJwtToken";

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [isAuthenticated, setIsAuthenticated] = useState(false); // 로그인 여부 상태
  const [userInfo, setUserInfo] = useState({
    name: "",
    id: "",
    level: "",
    role: "",
  }); // 유저 정보 상태

  // 새로고침시 token 등록
  useEffect(() => {
    const user = parseJwtToken();
    if (user) {
      setIsAuthenticated(true);
      setUserInfo(user);
    } else {
      setIsAuthenticated(false);
    }
  }, []);

  // 로그인 시 호출, 유저 정보 및 토큰 설정
  const login = (userId) => {
    console.log("[debug]: userId", userId);

    var user;
    user = parseJwtToken(userId);

    console.log("[debug]: jwt 에서 파싱한 유저객체", user);

    // 파싱한 유저객체를 전역 state에 추가
    if (user) {
      setUserInfo(user);
      setIsAuthenticated(true);
      return user.id;
    }

    // 토큰이 없을경우 바디로 받은 userId만
    setUserInfo((prev) => ({ ...prev, id: userId }));
    // 인증된 상태임을 표시
    setIsAuthenticated(true);
  };

  // 로그아웃 시 호출, 유저 정보 초기화
  const logout = () => {
    setIsAuthenticated(false);
    setUserInfo({ name: "", id: "", level: "", role: "" });

    // 로컬 스토리지에서 토큰과 유저 정보 삭제
    localStorage.removeItem("jwtToken");
    localStorage.removeItem("user");
  };

  return (
    <AuthContext.Provider value={{ isAuthenticated, userInfo, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};
