import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import Button from "../../components/Button";
import { signUpUser } from "../../services/user/UserService.mjs";

const SignUpPage = () => {
  // 폼 데이터를 하나의 객체로 관리
  const [formData, setFormData] = useState({
    email: "",
    role: "",
    privacyAgreed: false
  });

  // 유효성 검사 및 API 상태 관리
  const [status, setStatus] = useState({
    type: "", // "error" | "success" | ""
    message: ""
  });

  // 이메일 전송 중 상태 추가
  const [isLoading, setIsLoading] = useState(false);
  // 이메일 전송 성공 상태 추가
  const [isEmailSent, setIsEmailSent] = useState(false);

  const navigate = useNavigate();

  // 폼 입력값 변경을 처리하는 통합 핸들러
  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: type === "checkbox" ? checked : value
    }));
  };

  const handleSignUp = async () => {
    setStatus({ type: "", message: "" });
    setIsLoading(true);

    const emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;

    // 유효성 검사
    if (!formData.email || !emailRegex.test(formData.email)) {
      setStatus({ type: "error", message: "올바른 이메일 주소를 입력해주세요." });
      setIsLoading(false);
      return;
    }

    if (!formData.role) {
      setStatus({ type: "error", message: "가입 유형(강사/학생/학부모)을 선택해주세요." });
      setIsLoading(false);
      return;
    }

    if (!formData.privacyAgreed) {
      setStatus({ type: "error", message: "개인정보 수집 및 이용약관에 동의해주세요." });
      setIsLoading(false);
      return;
    }

    try {
      const response = await signUpUser({
        userEmail: formData.email,
        userRole: formData.role
      });

      if (response === "success") {
        setStatus({
          type: "success",
          message: "이메일이 전송되었습니다. 이메일을 확인해주세요."
        });
        setIsEmailSent(true); // 이메일 전송 성공 상태를 true로 설정
      } else {
        setStatus({
          type: "error",
          message: "회원가입에 실패했습니다. 다시 시도해주세요."
        });
      }
    } catch (error) {
      setStatus({
        type: "error",
        message: "회원가입 중 오류가 발생했습니다. 다시 시도해주세요."
      });
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div>
      <h2>회원가입</h2>
      {status.message && (
        <p style={{ color: status.type === "error" ? "red" : "green" }}>
          {status.message}
        </p>
      )}
      <input
        type="email"
        name="email"
        placeholder="이메일"
        value={formData.email}
        onChange={handleChange}
        disabled={isLoading || isEmailSent} // 로딩 중이거나 이메일 전송 성공 시 비활성화
      />
      <br />
      개인정보 수집 및 이용약관 동의
      <input
        type="checkbox"
        name="privacyAgreed"
        checked={formData.privacyAgreed}
        onChange={handleChange}
        disabled={isLoading || isEmailSent} // 로딩 중이거나 이메일 전송 성공 시 비활성화
      />
      <br />
      <label>
        <input
          type="radio"
          name="role"
          value="ROLE_TEACHER"
          checked={formData.role === "ROLE_TEACHER"}
          onChange={handleChange}
          disabled={isLoading || isEmailSent} // 로딩 중이거나 이메일 전송 성공 시 비활성화
        />
        강사
      </label>
      <label>
        <input
          type="radio"
          name="role"
          value="ROLE_STUDENT"
          checked={formData.role === "ROLE_STUDENT"}
          onChange={handleChange}
          disabled={isLoading || isEmailSent} // 로딩 중이거나 이메일 전송 성공 시 비활성화
        />
        학생
      </label>
      <label>
        <input
          type="radio"
          name="role"
          value="ROLE_PARENTS"
          checked={formData.role === "ROLE_PARENTS"}
          onChange={handleChange}
          disabled={isLoading || isEmailSent} // 로딩 중이거나 이메일 전송 성공 시 비활성화
        />
        학부모
      </label>
      <Button 
        onClick={handleSignUp} 
        disabled={isLoading || isEmailSent} // 로딩 중이거나 이메일 전송 성공 시 비활성화
      >
        {isLoading ? "이메일 전송 중..." : isEmailSent ? "이메일 전송 완료" : "회원가입"}
      </Button>
    </div>
  );
};

export default SignUpPage;
