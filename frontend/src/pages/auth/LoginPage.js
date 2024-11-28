import React, { useContext, useState } from 'react';
import { AuthContext } from '../../context/AuthContext';
import { useNavigate } from 'react-router-dom';
import Button from '../../components/Button';
import { loginUser } from '../../services/AuthService.mjs';

const LoginPage = () => {
  const { login } = useContext(AuthContext); // 로그인 함수 가져오기
  const [userEmail, setUserEmail] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const handleLogin = async() => {
    try{
      const { token, user } = await loginUser(userEmail, password)

      login({ token, user }); // context 를 로그인상태로 등록
      navigate('/'); // 로그인시 메인 페이지로 이동
    }catch(error) {
      console.log(error);

      //////////////임시로 로그인처리 아래 함수들 삭제해야함
      navigate('/');
      login({
        token: "dummy-token", // 임시로 더미 토큰
        user: { name: "김동은", id: "2", level: "7", role: 'teacher' }
      }); 
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