import React, { useEffect, useState } from "react";
import { readUserAttendance } from "../../../../services/classroom/AttendanceService.mjs";

const AttendanceUserList = () => {
  const [attendanceData, setAttendanceData] = useState([]);
  const [classId, date] = [1, 2014 - 12 - 24];

  useEffect(() => {
    const fetchAttendance = async () => {
      const response = await readUserAttendance();
      setAttendanceData(response);
    };
    fetchAttendance();
  }, []);

  return (
    <div>
      <h3>출석 현황 컴포넌트(유저별)</h3>
      {attendanceData.map((attendance) => (
        <p>
          {attendance.userId} - {attendance.date}
        </p>
      ))}
    </div>
  );
};

export default AttendanceUserList;
