import React from "react";
import { Link } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import Button from "../components/Button";

import "./Header.css";

const Header = ({ logout, isAuthenticated, userInfo }) => {
  const navigate = useNavigate(); // useNavigate 훅을 사용하여 네비게이션

  const handleLoginNavigate = () => {
    navigate("/login"); // 로그인 페이지로 이동
  };

  const handleLogoutNavigate = () => {
    logout();
    navigate("/"); // 로그아웃 페이지로 이동
  };

  const handleSignUpNavigate = () => {
    navigate("/signup"); // 로그아웃 페이지로 이동
  };

  return (
    <header>
      <nav>
        <div className="nav-left">
          <h1>이상한</h1>
          <Link to="/">홈</Link>
          <Link to="/about">소개</Link>
          <Link to="/teachers">강사목록</Link>
          <Link to="/students">학생목록</Link>
          <Link to="/classes">클래스목록</Link>
        </div>

        <div className="nav-right">
          {isAuthenticated ? (
            <>
              <Link to={`/myPage/${userInfo.id}`}>마이페이지</Link>
              {userInfo.role === "teacher" && (
                <>
                  {" "}
                  <Link to={`/teacherRoom/${userInfo.id}`}>교무실</Link>
                </>
              )}
              {userInfo.role === "student" && (
                <>
                  {" "}
                  <Link to={`/studentRoom/${userInfo.id}`}>자습실</Link>
                </>
              )}
              &nbsp; [{userInfo.role}] {userInfo.name} (Lv.{userInfo.level})
              &nbsp;
              <Button onClick={handleLogoutNavigate}>로그아웃</Button>{" "}
              {/* Button 컴포넌트 사용 */}
            </>
          ) : (
            <>
              <Button onClick={handleSignUpNavigate}>회원가입</Button>{" "}
              {/* Button 컴포넌트 사용 */}
              <Button onClick={handleLoginNavigate}>로그인</Button>{" "}
              {/* Button 컴포넌트 사용 */}
            </>
          )}
        </div>
      </nav>
    </header>
  );
};

export default Header;
