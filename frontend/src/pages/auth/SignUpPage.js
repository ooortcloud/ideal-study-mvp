import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import Button from "../../components/Button";
import { signUpUser } from "../../services/user/UserService.mjs";

const SignUpPage = () => {
  const [userRole, setUserRole] = useState(""); // 가입유형
  const [userEmail, setUserEmail] = useState("");
  const navigate = useNavigate();

  const handleSignUp = async () => {
    const response = await signUpUser({ userEmail, userRole });
    console.log(response);
    // TODO : response 에 따라 완료 or 오류 써야함
    navigate("/signup-complete");
  };

  return (
    <div>
      <h2>회원가입</h2>
      <input
        type="email"
        placeholder="이메일"
        value={userEmail}
        onChange={(e) => setUserEmail(e.target.value)}
      />
      <br />
      개인정보 수집 및 이용약관 동의 <input type="checkbox" />
      <br />
      <label>
        <input
          type="radio"
          name="userRole"
          value="teacher"
          onChange={(e) => {
            setUserRole(e.target.value);
          }}
        />
        강사
      </label>
      <label>
        <input
          type="radio"
          name="userRole"
          value="student"
          onChange={(e) => setUserRole(e.target.value)}
        />
        학생
      </label>
      <label>
        <input
          type="radio"
          name="userRole"
          value="parent"
          onChange={(e) => setUserRole(e.target.value)}
        />
        학부모
      </label>
      <Button onClick={handleSignUp}>회원가입</Button>
    </div>
  );
};

export default SignUpPage;
