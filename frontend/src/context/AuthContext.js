import React, { createContext, useState } from "react";

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [isAuthenticated, setIsAuthenticated] = useState(false); // 로그인 여부 상태
  const [userInfo, setUserInfo] = useState({
    name: "",
    id: "",
    level: "",
    role: "",
  }); // 유저 정보 상태

  // 로그인 시 호출, 유저 정보 및 토큰 설정
  const login = ({ username }) => {
    setIsAuthenticated(true);

    const token = localStorage.getItem("jwtToken");
    var user;
    console.log("[debug]: username", username);
    console.log("[debug]: localStorage 에서 꺼내온 토큰", token);

    if (token) {
      const decodedToken = JSON.parse(atob(token.split(".")[1])); // JWT의 페이로드 부분 파싱

      // Role mapping
      let role;
      console.log(typeof decodedToken.role);
      switch (decodedToken.role) {
        case "ROLE_TEACHER":
          role = "teacher";
          break;
        case "ROLE_STUDENT":
          role = "student";
          break;
        case "ROLE_PARENTS":
          role = "parent";
          break;
        default:
          role = ""; // 기본값 설정
      }
      console.log(role);

      user = {
        name: username, // 유저 이름은 인자로 받은 username 사용
        id: decodedToken.sub, // 파싱한 userId
        level: "", // 필요에 따라 추가
        role: role, // 파싱한 userRole
      };

      console.log("[debug]: 그걸 파싱한 토큰정보", decodedToken);
      console.log("[debug]: 토큰정보에서 추출한 유저정보", user);

      setUserInfo(user);
      return;
    }

    // 토큰이 없을경우 바디로 받은 username만
    setUserInfo((prev) => ({ ...prev, username }));
  };

  // 로그아웃 시 호출, 유저 정보 초기화
  const logout = () => {
    setIsAuthenticated(false);
    setUserInfo({ name: "", id: "", level: "", role: "" });

    // 로컬 스토리지에서 토큰과 유저 정보 삭제
    localStorage.removeItem("token");
    localStorage.removeItem("user");
  };

  return (
    <AuthContext.Provider value={{ isAuthenticated, userInfo, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};
