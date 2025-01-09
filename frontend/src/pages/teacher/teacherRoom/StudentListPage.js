import React, { useEffect, useState } from "react";
import { readEnrollmentsByClassId } from "../../../services/classroom/EnrollmentService.mjs";

const StudentListPage = ({ classId }) => {
  const [students, setStudents] = useState([]);
  useEffect(() => {
    const fetchStudents = async () => {
      const data = await readEnrollmentsByClassId(classId);
      setStudents(data.dtoList);
    };
    fetchStudents();
  }, [classId]);

  return (
    <div>
      <h3>학생 목록</h3>
      <p>자신에게 수업신청했거나, 수업중이거나, 수업완료한 학생목록을 봄</p>
      <ul>
        {students.map((student) => (
          <li key={student.enrollmentId}>
            <div>학생 ID: {student.studentId}</div>
            <div>수업 ID: {student.classroomId}</div>
            <div>등록자: {student.createdBy}</div>
            <div>상태: {student.status}</div>
            <div>현재 점수: {student.curScore}</div>
            <div>목표 점수: {student.targetScore}</div>
            <div>요청: {student.request}</div>
            <div>결정: {student.determination}</div>
          </li>
        ))}
      </ul>
    </div>
  );
};
export default StudentListPage;
