import React, { useState } from "react";
import useAuthStore from "../../stores/authStore";  // zustand store import
import { useNavigate } from "react-router-dom";
import Button from "../../components/Button";
import { loginUser } from "../../services/auth/AuthService.mjs";
import { trackLogin } from "../../utils/ga/event_tracking";

const LoginPage = () => {
  const login = useAuthStore((state) => state.login);  // useContext 대신 zustand 사용
  const [userEmail, setUserEmail] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const handleLogin = async () => {
    try {
      const { response, firstLoginResponse } = await loginUser(
        userEmail,
        password
      );

      const userId = login(response.data.username); // context 를 로그인상태로 등록
      
      // 로그인 성공 시 GA 이벤트 트래킹 - 이메일 정보 추가
      trackLogin('email', userEmail);

      if (firstLoginResponse.data === "first") {
        alert("최초 로그인! 회원 정보 수정 페이지로 이동(추후 구현 예정)");
        // navigate(`/myPage/${userId}`);
        navigate("/"); // 일단 회원 정보 수정 페이지는 나중에 구현
      } else {
        navigate("/"); // 로그인시 메인 페이지로 이동
      } // 로그인시 메인 페이지로 이동
    } catch (error) {
      console.log(error);
    }
  };

  return (
    <div>
      <h2>로그인</h2>
      <input
        type="email"
        placeholder="이메일"
        value={userEmail}
        onChange={(e) => setUserEmail(e.target.value)}
      />
      <input
        type="password"
        placeholder="비밀번호"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
      />
      <Button onClick={handleLogin}>로그인</Button>
    </div>
  );
};

export default LoginPage;
