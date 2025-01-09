import React from "react";
import { Link } from "react-router-dom";
import "./Sidebar.css";

const Sidebar = ({ location, userRole }) => {
  if (
    location !== "teacherRoom" &&
    location !== "studentRoom" &&
    location !== "parentRoom" &&
    location !== "myPage"
  ) {
    return null; // 사이드바를 없애는 부분
  }

  const sidebarClass =
    location === "teacherRoom" ||
    location === "studentRoom" ||
    location === "parentRoom"
      ? "sidebar room"
      : location === "myPage"
      ? "sidebar myPage"
      : "sidebar";

  return (
    <aside className={sidebarClass}>
      <h3>
        <Link
          to={
            location === "teacherRoom"
              ? "/teacherRoom"
              : location === "studentRoom"
              ? "/studentRoom"
              : location === "parentRoom"
              ? "/parentRoom"
              : "/myPage"
          }
        >
          {location === "teacherRoom" && userRole === "teacher"
            ? "교무실"
            : location === "studentRoom" && userRole === "student"
            ? "자습실"
            : location === "parentRoom" && userRole === "parent"
            ? "학부모실"
            : "마이페이지"}
        </Link>
      </h3>
      <nav>
        {location === "teacherRoom" && ( // Teacher links
          <>
            <Link to="/teacherRoom/classes">클래스 관리</Link>
            <Link to="/teacherRoom/students">학생 관리</Link>
            <Link to="/teacherRoom/assignments">강사 과제 관리</Link>
            <Link to="/teacherRoom/exams">강사 시험 관리</Link>
            <Link to="/teacherRoom/assignment-submissions">학생 과제 관리</Link>
            <Link to="/teacherRoom/exam-submissions">학생 시험 관리</Link>
          </>
        )}
        {location === "studentRoom" && ( // Student links
          <>
            <Link to="/studentRoom/classes">클래스 목록</Link>
            <Link to="/studentRoom/assignments">과제목록</Link>
            <Link to="/studentRoom/exams">시험목록</Link>
          </>
        )}
        {location === "parentRoom" && ( // Parent links
          <>
            <Link to="/parentRoom/attendance">학생 출석</Link>
            <Link to="/parentRoom/report">수업 리포트</Link>
          </>
        )}
        {location !== "teacherRoom" &&
          location !== "studentRoom" &&
          location !== "parentRoom" && ( // My Page links
            <>
              <Link to="/profile">개인정보 조회/수정</Link>
              <Link to="/settings">설정</Link>
            </>
          )}
      </nav>
    </aside>
  );
};

export default Sidebar;
