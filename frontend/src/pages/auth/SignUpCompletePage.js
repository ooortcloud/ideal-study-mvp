import React, { useEffect, useState } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';
import Button from '../../components/Button';
import apiClient from '../../services/apiClient.mjs';

const SignUpCompletePage = () => {
  const [searchParams] = useSearchParams();
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    // emailToken과 email을 한 번만 가져오기
    const emailToken = searchParams.get('emailToken');
    const email = searchParams.get('email');

    if (!emailToken || !email) {
      setError('유효하지 않은 인증 링크입니다. 회원가입을 다시 진행해주세요.');
      return;
    }

    const verifyEmail = async () => {

      try {
        const response = await apiClient.get('/users/email-authentication', {
          params: {
            emailToken,
            email
          }
        });
        setPassword(response.data);
      } catch (error) {
        setError('이메일 인증에 실패했습니다. 회원가입을 다시 진행해주세요.');
      }
    };

    verifyEmail();
  }, []); // 의존성 배열을 비워서 컴포넌트 마운트 시 한 번만 실행

  const handleGoToLogin = () => {
    navigate('/login');
  };

  if (error) {
    return (
      <div>
        <h2>이메일 인증 실패</h2>
        <p style={{ color: 'red' }}>{error}</p>
        <Button onClick={() => navigate('/signup')}>회원가입 다시하기</Button>
      </div>
    );
  }

  return (
    <div>
      <h2>회원가입이 완료되었습니다!</h2>
      {password ? (
        <>
          <p>발급된 임시 비밀번호: <strong>{password}</strong></p>
          <p>로그인 후 비밀번호를 변경해주세요.</p>
        </>
      ) : (
        <p>이메일 인증 진행 중...</p>
      )}
      <Button onClick={handleGoToLogin}>로그인 페이지로 이동</Button>
    </div>
  );
};

export default SignUpCompletePage;