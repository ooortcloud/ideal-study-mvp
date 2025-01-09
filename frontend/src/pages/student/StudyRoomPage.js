import React from "react";
import AttendanceUserList from "../../components/classroom/inClass/attendance/AttendanceUserList";

/**
- 과제 및 시험 목록 조회
- 클래스 정보 조회
*/

// 자습실
const StudyRoomPage = () => {
  return (
    <div>
      <h1>학생 자습실</h1>
      <AttendanceUserList />
    </div>
  );
};

export default StudyRoomPage;
