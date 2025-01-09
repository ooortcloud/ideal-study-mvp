import React, { useEffect, useState } from "react";
import { readClassAttendance } from "../../../../services/classroom/AttendanceService.mjs";

const AttendanceClassList = () => {
  const [attendanceData, setAttendanceData] = useState([]);
  const [classId, year, month] = [1, 2014, 12];

  useEffect(() => {
    const fetchAttendance = async () => {
      const response = await readClassAttendance(classId, year, month);
      setAttendanceData(response);
    };
    fetchAttendance();
  }, []);

  return (
    <div>
      <h3>출석 현황(클래스)</h3>
      {attendanceData.map((attendance) => (
        <p>
          {attendance.userId} - {attendance.date}
        </p>
      ))}
    </div>
  );
};

export default AttendanceClassList;
