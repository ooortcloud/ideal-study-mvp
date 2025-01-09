import React from "react";
import AttendanceClassList from "../../../../components/classroom/inClass/attendance/AttendanceClassList";
import AttendanceUserList from "../../../../components/classroom/inClass/attendance/AttendanceUserList";
import Button from "../../../../components/Button";
import {
  checkInAttendance,
  checkOutAttendance,
} from "../../../../services/classroom/AttendanceService.mjs";

const AttendancePage = () => {
  const userId = "얼음루비"; // TODO : 수정필요

  const handleAttendance = async () => {
    const result = await checkInAttendance(userId);
    if (result == null) alert(`${userId}님 출석완료`);
  };

  const handleLeave = async () => {
    const result = await checkOutAttendance(userId);
    if (result == null) alert(`${userId}님 퇴실완료`);
  };

  return (
    <div>
      <h2>출석</h2>
      <Button onClick={handleAttendance}>출석</Button>
      <Button onClick={handleLeave}>퇴실</Button>
      <AttendanceClassList />
      <AttendanceUserList />
    </div>
  );
};

export default AttendancePage;
